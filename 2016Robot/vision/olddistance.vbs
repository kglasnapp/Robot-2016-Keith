
list = GetArrayVariable("BFR_COORDINATES")
if isArray(list) then
  if ubound(list) > 0 then

		targetPixelHeight = 0
    targetSamples = 0

    ' calibrated for an Axis camera
    imageHeight = GetVariable("IMAGE_HEIGHT")
SetVariable "ImageHeight", imageHeight
  	cameraFieldOfView = 45.0
	  targetHeight = 36.0

    for i = 0 to ubound(list) step 8
      ' grab list of coordinates from blob_replace
	  	' note the array is sorted by previous module in a specific order
      righty = list(i+1)
      rightyy = list(i+7)
      lefty = list(i+3)
      leftyy = list(i+5)

			' based on these two side lines get the center line height
		  ' the center line is used since we want to aim to the center
  		' of the target. This also removes any perspective distortion
	  	' where the right or left size may be a couple inches closer
		  ' or futher away from the camera
  		targetPixelHeight = targetPixelHeight + ((lefty - leftyy) + (righty - rightyy)) / 2

			targetSamples = targetSamples + 1

    next


		targetPixelHeight = targetPixelHeight / targetSamples

    ' we can use a known distance to determine FOV if we don't know it
	  ' measuredDistance = 10.0*12.0
 	  ' write "Calculated FOV " & (((atan((((targetHeight*imageHeight)/targetPixelHeight)/2)/measuredDistance)*2.0)*180.0)/3.14159) & vbCRLF
		' totalDistance = (((targetHeight*imageHeight)/targetPixelHeight)/2)/tan(((cameraFieldOfView*3.14159)/180.0)/2.0)


    ' determine distance in inches
		numerator = (((targetHeight*imageHeight)/targetPixelHeight)/2)
		denomerator = tan(((cameraFieldOfView*3.14159)/180.0)/2.0)
    totalDistance = numerator / denomerator
   
		SetVariable "denomerator", denomerator
	  SetVariable "numerator", numerator
		
		tempDistance = CDbl((totalDistance*100)/12)/100
		finalDistance = tempDistance
		SetVariable "Distance", finalDistance
		bfrCoordinates = GetArrayVariable("BFR_COORDINATES")


	end if
end if




