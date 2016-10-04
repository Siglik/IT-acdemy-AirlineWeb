/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.delete;

import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.DeleteAction;

/**
 * @author qqq175
 *
 */
public abstract class DeleteEntityAction implements DeleteAction {

	/* (non-Javadoc)
	 * @see org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action#execute(org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent)
	 */
	@Override
	public abstract SessionRequestContent execute(SessionRequestContent requestContent);
	
	@Override
	public boolean deleteEntityById(){
		return true;
	}
}
