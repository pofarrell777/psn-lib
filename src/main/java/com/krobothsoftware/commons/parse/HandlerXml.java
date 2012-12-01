/* ===================================================
 * Copyright 2012 Kroboth Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== 
 */

package com.krobothsoftware.commons.parse;

import javax.xml.parsers.SAXParser;

import com.krobothsoftware.commons.progress.ProgressHelper;

/**
 * Base Handler for XML data
 * 
 * @version 3.0
 * @since Nov 25 2012
 * @author Kyle Kroboth
 */
public abstract class HandlerXml extends Handler {
	protected SAXParser xmlParser;
	protected String startTag;
	protected boolean calledStartElement;

	void setParser(final SAXParser xmlParser) {
		this.xmlParser = xmlParser;
	}

	public HandlerXml(final ProgressHelper progressHelper) {
		super(progressHelper);
	}

	public HandlerXml() {

	}

	/**
	 * Gets correct qlocal from XML Handler
	 * 
	 * @param qName
	 *            qname
	 * @param localname
	 *            localname
	 * @return correct qLocal
	 */
	protected final String qLocal(final String qName, final String localname) {
		return qName;
	}

}
