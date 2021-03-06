/***********************************************************************
WaldoLocatorTool - Class for 6-DOF localization with scaled-down
transformations while the tool button is pressed.
Copyright (c) 2006-2010 Oliver Kreylos

This file is part of the Virtual Reality User Interface Library (Vrui).

The Virtual Reality User Interface Library is free software; you can
redistribute it and/or modify it under the terms of the GNU General
Public License as published by the Free Software Foundation; either
version 2 of the License, or (at your option) any later version.

The Virtual Reality User Interface Library is distributed in the hope
that it will be useful, but WITHOUT ANY WARRANTY; without even the
implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with the Virtual Reality User Interface Library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
02111-1307 USA
***********************************************************************/

#ifndef VRUI_WALDOLOCATORTOOL_INCLUDED
#define VRUI_WALDOLOCATORTOOL_INCLUDED

#include <Geometry/OrthogonalTransformation.h>
#include <Vrui/LocatorTool.h>

namespace Vrui {

/* Forward declarations: */
class WaldoLocatorTool;

class WaldoLocatorToolFactory:public ToolFactory
	{
	friend class WaldoLocatorTool;
	
	/* Elements: */
	private:
	Scalar linearScale; // Scale factor for linear motions
	Scalar angularScale; // Scale factor for angular motions
	
	/* Constructors and destructors: */
	public:
	WaldoLocatorToolFactory(ToolManager& toolManager);
	virtual ~WaldoLocatorToolFactory(void);
	
	/* Methods: */
	virtual const char* getName(void) const;
	virtual Tool* createTool(const ToolInputAssignment& inputAssignment) const;
	virtual void destroyTool(Tool* tool) const;
	};

class WaldoLocatorTool:public LocatorTool
	{
	friend class WaldoLocatorToolFactory;
	
	/* Elements: */
	private:
	static WaldoLocatorToolFactory* factory; // Pointer to the factory object for this class
	
	/* Transient dragging state: */
	bool active; // Flag if the locator tool is active (i.e., button is pressed)
	NavTrackerState initial; // Transformation before dragging started
	NavTrackerState increment; // Accumulated incremental transformation
	NavTrackerState last; // Last used input device transformation
	
	/* Constructors and destructors: */
	public:
	WaldoLocatorTool(const ToolFactory* factory,const ToolInputAssignment& inputAssignment);
	
	/* Methods from Tool: */
	virtual const ToolFactory* getFactory(void) const;
	virtual void buttonCallback(int buttonSlotIndex,InputDevice::ButtonCallbackData* cbData);
	virtual void frame(void);
	};

}

#endif
