#!/bin/bash
# Enter SARndbox directory:
cd ~/src/SARndbox
# Run SARndbox with proper command line arguments:
make && ./bin/SARndbox -uhm -fpv -evr -0.01 -rs 2.0 -ucl 1.5
