/*
 * Copyright 2003-2006 The Apache Software Foundation.

 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file was auto-generated by the Axis C++ Web Service Generator (WSDL2Ws)
 * This file contains functions to manipulate complex type SOAPStruct_Array
 */

#include "SOAPStruct_Array.hpp"

SOAPStruct_Array::SOAPStruct_Array()
{
	m_Type = USER_TYPE;
	m_belongsToAxisEngine = false;
}

SOAPStruct_Array::SOAPStruct_Array(const SOAPStruct_Array & original)
{
	m_Type = USER_TYPE;
	m_belongsToAxisEngine = false;
	clone(original);
}

SOAPStruct_Array::~SOAPStruct_Array()
{
	clear();
}

void SOAPStruct_Array::set(SOAPStruct** array, const int size)
{
	clear();
	m_Size = size;
	m_Type = USER_TYPE;

	if (m_Size == 0)
	{
		m_Array = NULL;
	}
	else
	{
		m_Array = new void*[m_Size];
		for (int count = 0 ; count < m_Size ; count++ )
		{
			if (array[count] == NULL)
			{
				m_Array[count] = NULL;
			}
			else
			{
				((SOAPStruct**) m_Array)[count] =
					new SOAPStruct(*((SOAPStruct**) array)[count]);
			}
		}
	}
}

SOAPStruct** SOAPStruct_Array::get(int & size) const
{
	XSDTYPE type;
	return (SOAPStruct**) Axis_Array::get(size, type);
}

void SOAPStruct_Array::clone(const SOAPStruct_Array & original)
{
	set((SOAPStruct**) original.m_Array, original.m_Size);
}

Axis_Array * SOAPStruct_Array::clone() const
{
	return new SOAPStruct_Array(*this);
}

void SOAPStruct_Array::clear()
{
	if (m_Array != NULL)
	{
		if (m_Size > 0)
		{
			for (int count = 0 ; count < m_Size ; count++)
			{
				if (m_Array[count] != NULL)
				{
					delete ((SOAPStruct**) m_Array)[count];
					m_Array[count] = NULL;
				}
			}
		}
		if (m_belongsToAxisEngine == false)
		{
			delete [] m_Array;
			m_Array = NULL;
			m_Size = 0;
		}
		else
		{
			// Allow the engine to clear up it's memory
			Axis_Array::clear();
		}
		m_belongsToAxisEngine = false;
	}
}

