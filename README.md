# SARndbox
Augmented reality application scanning a sand surface using a Kinect 3D camera, and projecting a real- time updated topography map with topographic contour lines, hillshading, and an optional real-time water flow simulation back onto the sand surface using a calibrated projector

This code that was originally from UC Davis was modified to further model flood-related scenarios.

Thesis: __Simulating Flood-related Disaster Scenarios Using an Augmented Reality Sandbox__
Authors: Faine Mabry A. Fonseca, Raphael S. Remorosa, Aaron Cole T. See
Advisors: Paolo Antonio G. Gonzalez, Ma. Mercedes T. Rodrigo, Ph.D

Deviations from the code are as follows:
 - Modified internal GUI to allow users to modify several variables chosen by the researchers.
 - External GUI made from Java to allow users to initialize the scale and the evaporation rate of the SARndbox with ease.
 - Addition of an experimental seepage code that uses the drain feature of the SARndbox.

Recommendations:
 - Find the depth of a point in the SARndbox to determine if that point is flooded.
 - More accurate seepage code (localization of the draining function).
 - Find a way to determine how much of a flood was reduced via factors (man-made and natural structures).
 - Varying rainfall function.
