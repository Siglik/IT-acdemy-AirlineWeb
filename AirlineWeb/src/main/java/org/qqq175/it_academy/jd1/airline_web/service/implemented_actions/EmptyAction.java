/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.implemented_actions;

import org.qqq175.it_academy.jd1.airline_web.service.Action;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;

/**
 * @author qqq175
 *
 */
public class EmptyAction implements Action {
	/* (non-Javadoc)
	 * @see org.qqq175.it_academy.jd1.airline_web.service.Action#execute(org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent)
	 */
	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
		requestContent.setAttribute("mainform", JSPPathManager.getProperty("form.login"));
		return requestContent;
	}

}
