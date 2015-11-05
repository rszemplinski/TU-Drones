import time, pdb, log
import drone_modes as mode
from dronekit.lib import VehicleMode
from pymavlink import mavutil

class DroneTools:

    def __init__(self, vehicle):
        self.vehicle = vehicle

    def pre_arm_checks(self):
        print "Running through pre-arm checks..."
        self.initialise()
        #self.wait_for_gps()

    def initialise(self):
        while self.vehicle.mode.name == "INITIALISING":
            print "Waiting for the vehicle to initialise"
            time.sleep(1)

    def wait_for_gps(self):
        while self.vehicle.gps_0.fix_type < 2:
            print "Waiting for GPS... : ", self.vehicle.gps_0.fix_type
            time.sleep(1)

    def arm_and_takeoff(self, aTargetAltitude):
        """
        Arms vehicle and fly to aTargetAltitude.
        """

        self.pre_arm_checks()
        self.arm_vehicle()
        self.wait_for_arming()

        print "Taking off!"
        self.vehicle.commands.takeoff(aTargetAltitude) # Take off to target altitude
        self.vehicle.flush()

        # Wait until the vehicle reaches a safe height before processing the goto (otherwise the command
        #  after Vehicle.commands.takeoff will execute immediately).
        while True:
            print " Altitude: ", self.vehicle.location.alt
            if self.vehicle.location.alt>=aTargetAltitude*0.95: #Just below target, in case of undershoot.
                print "Reached target altitude"
                break;
            time.sleep(1)

    def prepare_for_takeoff(self):
        self.vehicle.mode = VehicleMode('STABILIZE')
        self.vehicle.armed = True
        self.vehicle.flush()
        while not self.vehicle.mode.name=='STABILIZE' and not self.vehicle.armed:
            print 'Getting ready for takeoff!'
            time.sleep(1)

    def reset(self):
        print "Reset vehicle attributes/parameters and exit"
        self.vehicle.mode = VehicleMode("STABILIZE")
        self.vehicle.armed = False
        self.vehicle.parameters['THR_MIN']=130
        self.vehicle.flush()

    def add_observer(self, type, callback):
        self.vehicle.add_attribute_observer(type, callback)
        self.vehicle.flush()

    def remove_observer(self, type, callback):
        self.vehicle.remove_attribute_observer(type, callback)
        self.vehicle.flush()

    def wait_for_arming(self):
        while not self.vehicle.armed:
            print " Waiting for arming..."
            time.sleep(1)

    def arm_vehicle(self):
        print "Arming motors"
        # Copter should arm in STABILIZE mode
        self.vehicle.mode    = VehicleMode("STABILIZE")
        self.vehicle.armed   = True
        self.vehicle.flush()

    def disarm_vehicle(self):
        print "Disarming Vehicle"
        self.vehicle.armed = False
        self.vehicle.flush()

    def set_mode(self, mode):
        print "Setting mode to " + mode
        self.vehicle.mode = VehicleMode(mode)
        self.vehicle.flush()

    def get_parameter(self, name):
        return self.vehicle.parameters[name]

    def set_parameter(self, name, val):
        self.vehicle.parameters[name] = val
        self.vehicle.flush()

    def get_cmds(self):
        cmds = self.vehicle.commands
        cmds.download()
        cmds.wait_valid()
        return cmds

    """
      velocity_x > 0 => fly North
      velocity_x < 0 => fly South
      velocity_y > 0 => fly East
      velocity_y < 0 => fly West
      velocity_z < 0 => ascend
      velocity_z > 0 => descend
    """
    def set_velocity(self, v_x, v_y, v_z):
        msg = self.vehicle.message_factory.set_position_target_local_ned_encode(
            0,      #time_boot_ms (not used)
            0, 0,   #target system, target component
            mavutil.mavlink.MAV_FRAME_BODY_NED, #frame
            0b0000111111000111, #type mask (only speeds enabled)
            0, 0, 0, #x, y, z (not used)
            v_x, v_y, v_z,
            0, 0, 0, #x, y, z acceleration (not supported yet)
            0, 0
        )
        self.vehicle.send_mavlink(msg)
        self.vehicle.flush()

    def set_yaw(self, heading, relative=False):
        is_relative = 1 if relative else 0

        msg = self.vehicle.message_factory.command_long_encode(
            0, 0,   #target system, target component
            mavutil.mavlink.MAV_CMD_CONDITION_YAW, #command
            0, #confirmation
            heading,    #param 1, yaw in degrees
            0,          #param 2, yaw speed deg/s
            1,          #param 3, direction -1 ccw, 1 cw
            is_relative, #param 4, relative offset 1, absolute angle 0
            0, 0, 0
        )

        self.vehicle.send_mavlink(msg)
        self.vehicle.flush()

    def set_speed(self, speed):
        msg = self.vehicle.message_factory.command_long_encode(
            0, 0,    # target system, target component
            mavutil.mavlink.MAV_CMD_DO_CHANGE_SPEED, #command
            0, #confirmation
            0, #param 1
            speed, # speed in metres/second
            0, 0, 0, 0, 0 #param 3 - 7
            )

        # send command to vehicle
        self.vehicle.send_mavlink(msg)
        self.vehicle.flush()

    """
      ROI == Region of Interest
      Have no clue what this is for
    """
    def set_roi(self, location):
        # create the MAV_CMD_DO_SET_ROI command
        msg = self.vehicle.message_factory.command_long_encode(
            0, 0,    # target system, target component
            mavutil.mavlink.MAV_CMD_DO_SET_ROI, #command
            0, #confirmation
            0, 0, 0, 0, #params 1-4
            location.lat,
            location.lon,
            location.alt
            )
        # send command to vehicle
        self.vehicle.send_mavlink(msg)
        self.vehicle.flush()

    def set_home(self, aLocation, aCurrent=1):
        msg = self.vehicle.message_factory.command_long_encode(
            0, 0,    # target system, target component
            mavutil.mavlink.MAV_CMD_DO_SET_HOME, #command
            0, #confirmation
            aCurrent, #param 1: 1 to use current position, 2 to use the entered values.
                0, 0, 0, #params 2-4
            aLocation.lat,
            aLocation.lon,
            aLocation.alt
            )
        # send command to vehicle
        self.vehicle.send_mavlink(msg)
        self.vehicle.flush()

    def get_vehicle_status(self):
        print "Get all vehicle attribute values:"
        print "Location: %s" % self.vehicle.location
        print "Attitude: %s" % self.vehicle.attitude
        print "Velocity: %s" % self.vehicle.velocity
        print "GPS: %s" % self.vehicle.gps_0
        print "Groundspeed: %s" % self.vehicle.groundspeed
        print "Airspeed: %s" % self.vehicle.airspeed
        print "Mount status: %s" % self.vehicle.mount_status
        print "Battery: %s" % self.vehicle.battery
        print "Rangefinder: %s" % self.vehicle.rangefinder
        print "Rangefinder distance: %s" % self.vehicle.rangefinder.distance
        print "Rangefinder voltage: %s" % self.vehicle.rangefinder.voltage
        print "Mode: %s" % self.vehicle.mode.name    # settable
        print "Armed: %s" % self.vehicle.armed    # settable

    def close():
        self.vehicle.close()
