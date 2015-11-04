from dronekit import connect
from tools.drone_tools import DroneTools
import tools.log as log
import tools.drone_modes as mode
import ipdb, time

log.start_log()

print "Waiting for vehicle..."
vehicle = connect("/dev/ttyACM0", await_params=True)

drone = DroneTools(vehicle)

log.stop_log()

ipdb.set_trace()
