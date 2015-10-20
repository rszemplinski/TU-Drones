import os, sys
sys.path.append(os.getcwd() + "/TU-Drones")

from pymavlink import mavutil
from tools.drone_tools import DroneTools
import time

api = local_connect()
vehicle = api.get_vehicles()[0]

tools = DroneTools(api, vehicle);

tools.arm_vehicle()
tools.wait_for_arming()
time.sleep(2)

print "Landing!"
tools.disarm_vehicle()
