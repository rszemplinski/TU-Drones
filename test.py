from dronekit import connect
from tools.drone_tools import DroneTools
import tools.log as log
import tools.drone_modes as mode
import ipdb, time

log.stop_log()

vehicle = connect('/dev/ttyUSB2', await_params = True)

tools = DroneTools(vehicle);

ipdb.set_trace()
