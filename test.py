import os, sys
sys.path.append(os.getcwd() + "/TU-Drones")

from pymavlink import mavutil
from tools.drone_tools import *

api = local_connect()
vehicle = api.get_vehicles()[0]

arm_vehicle(vehicle)
