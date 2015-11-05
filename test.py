from dronekit import connect
from tools.drone_tools import DroneTools
import tools.log as log
import tools.drone_modes as mode
import ipdb, time

drone = DroneTools(vehicle)

log.stop_log()
=======
vehicle = connect('COM6', await_params = True)

tools = DroneTools(vehicle);

ipdb.set_trace()
