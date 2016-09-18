/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service;

import org.qqq175.it_academy.jd1.airline_web.service.implemented_actions.EmptyAction;
import org.qqq175.it_academy.jd1.airline_web.service.implemented_actions.LoginAction;
import org.qqq175.it_academy.jd1.airline_web.service.implemented_actions.LogoutAction;
import org.qqq175.it_academy.jd1.airline_web.service.implemented_actions.ShowAllCitiesAction;
import org.qqq175.it_academy.jd1.airline_web.service.implemented_actions.ShowAllFlightsAction;
import org.qqq175.it_academy.jd1.airline_web.service.implemented_actions.ShowAllHubsAction;
import org.qqq175.it_academy.jd1.airline_web.service.implemented_actions.ShowAllRoutesAction;

/**
 * @author qqq175
 *
 */
public enum ActionEnum {
	NONE {
		{
			this.action = new EmptyAction();
		}
	},
	UNKNOWN {
		{
			this.action = new EmptyAction();
		}
	},
	LOGIN {
		{
			this.action = new LoginAction();
		}
	},
	LOGOUT {
		{
			this.action = new LogoutAction();
		}
	},
	SHOW_FLIGHTS {
		{
			this.action = new ShowAllFlightsAction();
		}
	},
	SHOW_ROUTES  {
		{
			this.action = new ShowAllRoutesAction();
		}
	},
	SHOW_CITIES {
		{
			this.action = new ShowAllCitiesAction();
		}
	},
	SHOW_HUBS {
		{
			this.action = new ShowAllHubsAction();
		}
	};
	Action action;

	public Action getAction() {
		return action;
	}
}
