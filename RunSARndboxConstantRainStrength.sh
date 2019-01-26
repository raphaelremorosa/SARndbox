#!/bin/bash
# Enter SARndbox directory:
cd ~/src/SARndbox
# Run SARndbox with proper command line arguments:
make && ./bin/SARndbox -uhm -fpv -evr 0.03 -ws 10.0 30
