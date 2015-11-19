from dronekit import connect
from tools.drone_tools import DroneTools
import tools.log as log
import tools.drone_modes as mode
import ipdb, time, dronekit

import argparse

parser = argparse.ArgumentParser(description="This is just a test.")
parser.add_argument('--connect', default="/dev/ttyUSB0", help="Vehicle connection target. (Example: /dev/ttyUSB0)")
parser.add_argument('--baud', default="57600", help="Connection Baud Rate. (Example: 57600)")
args = parser.parse_args()

print("Waiting for vehicle...")
vehicle = connect(ip=args.connect, baud=args.baud, wait_ready=False)

# print("Tools are activated...")
# tools = DroneTools(vehicle)

vehicle.armed = True
vehicle.channels.overrides['3'] = 1500;

ipdb.set_trace()
