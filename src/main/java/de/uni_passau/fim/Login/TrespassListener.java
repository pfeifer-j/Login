// =============================================================================
//
//   TrespassListener.java
//
//   (c) 2022, Christian Bachmaier <bachmaier@fim.uni-passau.de>
//
// =============================================================================

package de.uni_passau.fim.Login;

import java.util.Map;

import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;

/**
 * Checks requests on user authentication.
 */
public class TrespassListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext fctx = event.getFacesContext();
		ExternalContext ctx = fctx.getExternalContext();
		Map<String, Object> sessionMap = ctx.getSessionMap();

		// Is the user on the login page?
		boolean publicArea = false;
		UIViewRoot viewRoot = fctx.getViewRoot();
		if (viewRoot != null) {
			String url = viewRoot.getViewId();
			publicArea = url.endsWith("login.xhtml");
		}

		// Is the user already authenticated?
		boolean loggedIn = sessionMap.containsKey("loggedin");

		if (!publicArea && !loggedIn) {
			// Illegal request.

			/*
			 * // Define error message. FacesMessage fmsg = new
			 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Log in first.", null);
			 * fctx.addMessage(null, fmsg);
			 * 
			 * // Let the faces messages of fctx also live in the next request. The // flash
			 * scope lives exactly for two subsequent requests.
			 * ctx.getFlash().setKeepMessages(true);
			 * 
			 * // Redirect to the login page. NavigationHandler nav =
			 * fctx.getApplication().getNavigationHandler(); nav.handleNavigation(fctx,
			 * null, "login.xhtml?faces-redirect=true");
			 * 
			 * // Stop the life cycle for this request. fctx.responseComplete();
			 */
		}
	}

}

// -----------------------------------------------------------------------------
// end of file
// -----------------------------------------------------------------------------
