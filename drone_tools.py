from droneapi.lib import VehicleMode
from pymavlink import mavutil
import time
import pdb

api = local_connect()
vehicle = api.get_vehicles()[0]

def get_vehicle_status():
    print "\nGet all vehicle attribute values:"
    print "\nLocation: %s" % vehicle.location
    print "\nAttitude: %s" % vehicle.attitude
    print "\nVelocity: %s" % vehicle.velocity
    print "\nGPS: %s" % vehicle.gps_0
    print "\nGroundspeed: %s" % vehicle.groundspeed
    print "\nAirspeed: %s" % vehicle.airspeed
    print "\nMount status: %s" % vehicle.mount_status
    print "\nBattery: %s" % vehicle.battery
    print "\nRangefinder: %s" % vehicle.rangefinder
    print "\nRangefinder distance: %s" % vehicle.rangefinder.distance
    print "\nRangefinder voltage: %s" % vehicle.rangefinder.voltage
    print "\nMode: %s" % vehicle.mode.name    # settable
    print "\nArmed: %s" % vehicle.armed    # settable

def pre_arm_checks():
    print "\nRunning through pre-arm checks..."
    initialise()
    wait_for_gps()

def initialise():
    while vehicle.mode.name == "INITIALSING":
        print "\nWaiting for the vehicle to initialise"
        time.sleep(1)

def wait_for_gps():
    while vehicle.gps_0.fix_type < 2:
        print "\nWaiting for GPS... : ", vehicle.gps_0.fix_type
        time.sleep(1)

def prepare_for_takeoff():
    vehicle.mode = VehicleMode('GUIDED')
    vehicle.armed = True
    vehicle.flush()
    while not vehicle.mode.name=='GUIDED' and not vehicle.armed and not api.exit:
        print '\nGetting ready for takeoff!'
        time.sleep(1)

def reset():
    print "\nReset vehicle attributes/parameters and exit"
    vehicle.mode = VehicleMode("STABILIZE")
    vehicle.armed = False
    vehicle.parameters['THR_MIN']=130
    vehicle.flush()

def add_observer(type, callback):
    vehicle.add_attribute_observer(type, callback)
    vehicle.flush()

def remove_observer(type, callback):
    vehicle.remove_attribute_observer(type, callback)
    vehicle.flush()

def arm_vehicle():
    vehicle.armed = True
    vehicle.flush()

def disarm_vehicle():
    vehicle.armed = False
    vehicle.flush()

def set_mode(mode):
    vehicle.mode = VehicleMode(mode)
    vehicle.flush()

def set_parameter(name, val):
    vehicle.parameters[name] = val
    vehicle.flush()

def get_cmds():
    cmds = vehicle.commands
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
def set_velocity(v_x, v_y, v_z):
    msg = vehicle.message_factory.set_position_target_local_ned_encode(
        0,      #time_boot_ms (not used)
        0, 0,   #target system, target component
        mavutil.mavlink.MAV_FRAME_BODY_NED, #frame
        0b0000111111000111, #type mask (only speeds enabled)
        0, 0, 0, #x, y, z (not used)
        v_x, v_y, v_z,
        0, 0, 0, #x, y, z acceleration (not supported yet)
        0, 0
    )
    vehicle.send_mavlink(msg)
    vehicle.flush()

def set_yaw(heading, relative=False):
    is_relative = 1 if relative else 0

    msg = vehicle.message_factory.command_long_encode(
        0, 0,   #target system, target component
        mavutil.mavlink.MAV_CMD_CONDITION_YAW, #command
        0, #confirmation
        heading,    #param 1, yaw in degrees
        0,          #param 2, yaw speed deg/s
        1,          #param 3, direction -1 ccw, 1 cw
        is_relative, #param 4, relative offset 1, absolute angle 0
        0, 0, 0
    )

    vehicle.send_mavlink(msg)
    vehicle.flush()

def set_speed(speed):
    msg = vehicle.message_factory.command_long_encode(
        0, 0,    # target system, target component
        mavutil.mavlink.MAV_CMD_DO_CHANGE_SPEED, #command
        0, #confirmation
        0, #param 1
        speed, # speed in metres/second
        0, 0, 0, 0, 0 #param 3 - 7
        )

    # send command to vehicle
    vehicle.send_mavlink(msg)
    vehicle.flush()

"""
  ROI == Region of Interest
  Have no clue what this is for
"""
def set_roi(location):
    # create the MAV_CMD_DO_SET_ROI command
    msg = vehicle.message_factory.command_long_encode(
        0, 0,    # target system, target component
        mavutil.mavlink.MAV_CMD_DO_SET_ROI, #command
        0, #confirmation
        0, 0, 0, 0, #params 1-4
        location.lat,
        location.lon,
        location.alt
        )
    # send command to vehicle
    vehicle.send_mavlink(msg)
    vehicle.flush()

def set_home(aLocation, aCurrent=1):
    msg = vehicle.message_factory.command_long_encode(
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
    vehicle.send_mavlink(msg)
    vehicle.flush()
