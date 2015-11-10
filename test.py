from dronekit import connect
from tools.drone_tools import DroneTools
import tools.log as log
import tools.drone_modes as mode
import ipdb, time, dronekit

log.stop_log()

print "Starting connection..."

vehicle = connect('/dev/ttyUSB0', baud=57600)

tools = DroneTools(vehicle);

ipdb.set_trace()
