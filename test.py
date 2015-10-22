import os, sys
sys.path.append(os.getcwd() + "/TU-Drones")

import tools.drone_modes as mode
from tools.drone_tools import DroneTools
import time
import pdb

api = local_connect()
vehicle = api.get_vehicles()[0]

tools = DroneTools(api, vehicle);

tools.arm_vehicle()
tools.set_speed(5)
time.sleep(3)
tools.disarm_vehicle()
