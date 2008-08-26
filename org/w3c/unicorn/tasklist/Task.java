// $Id: Task.java,v 1.2 2008-08-26 15:26:37 fbatard Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3.unicorn.tasklist.ExecType;
import org.w3.unicorn.tasklist.TaskType;
import org.w3.unicorn.tasklist.IfType;
import org.w3.unicorn.tasklist.ThenType;
import org.w3.unicorn.tasklist.CondType;
import org.w3c.unicorn.tasklist.parameters.Parameter;
import org.w3c.unicorn.tasklisttree.TLTCond;
import org.w3c.unicorn.tasklisttree.TLTExec;
import org.w3c.unicorn.tasklisttree.TLTIf;
import org.w3c.unicorn.tasklisttree.TLTNode;
import org.w3c.unicorn.util.LocalizedString;

/**
 * Task<br />
 * Created: May 29, 2006 5:53:12 PM<br />
 */
public class Task {

	private static final Log logger = LogFactory
			.getLog("org.w3c.unicorn.tasklist");

	/**
	 * Id of the task
	 */
	private String sID;

	/**
	 * Longnames of the task
	 */
	private LocalizedString aLocalizedStringLongName;

	/**
	 * Descriptions of the task
	 */
	private LocalizedString aLocalizedStringDescription;

	
	/**
	 * Parameters of the task
	 */
	private Map<String, Parameter> mapOfTaskParameter;

	/**
	 * References to other tasks
	 */
	private List<String> listOfReference;

	/**
	 * Used to expand the task
	 */
	private boolean bExpandingOrExpanded = false;

	/**
	 * Root of the execution level tree
	 */
	private TLTNode root;


	/**
	 * Creates a new Task.
	 */
	public Task() {
		this.sID = "";
		this.aLocalizedStringLongName = new LocalizedString();
		this.aLocalizedStringDescription = new LocalizedString();
		this.mapOfTaskParameter = new LinkedHashMap<String, Parameter>();
		this.listOfReference = new ArrayList<String>();
	}
	
	/**
	 * Allows to display the tree of execution level
	 * 
	 * @param root
	 *            the node to display
	 */
	public void displayTree(TLTNode root) {
		for (TLTExec exec : root.getExecutionList()) {
			System.out.println(exec);
		}
		for (TLTIf ifs : root.getIfList()) {
			displayTree(ifs.getIfOk());
			for (TLTCond conds : ifs.getCondArray())
				System.out.println(conds);
			displayTree(ifs.getIfNotOk());
		}
	}

	/**
	 * Get the root of the execution level tree
	 * @return the root of the tree
	 */
	public TLTNode getTree() {
		return this.root;
	}
	
	
	/**
	 * Set the root of the execution level tree
	 */
	public void setTree(TLTNode root) {
		this.root=root;
	}
	


	/**
	 * Creates a new Task.
	 * 
	 * @param aLocalizedStringDescription
	 * @param sID
	 * @param aLocalizedStringLongName
	 * @param mapOfParameter
	 * @param mapOfObservation
	 */
	public Task(final String sID,
			final LocalizedString aLocalizedStringDescription,
			final LocalizedString aLocalizedStringLongName,
			final Map<String, Parameter> mapOfParameter) {
		super();
		this.aLocalizedStringDescription = aLocalizedStringDescription;
		this.sID = sID;
		this.aLocalizedStringLongName = aLocalizedStringLongName;
		this.mapOfTaskParameter = mapOfParameter;
		this.listOfReference = new ArrayList<String>();
	}

	/**
	 * Returns the internationalized description of this task
	 * 
	 * @return Returns the description.
	 */
	public LocalizedString getDescription() {
		return this.aLocalizedStringDescription;
	}

	/**
	 * Returns a localized description of this task
	 * 
	 * @param sLang
	 *            requested locale (e.g. en, fr, zn-ch, ...)
	 * @return the localized description corresponding to the locale
	 */
	public String getDescription(final String sLang) {
		final String sDesc = this.aLocalizedStringDescription
				.getLocalization(sLang);
		if (sDesc == null) {
			return "";
		}
		return sDesc;
	}

	/**
	 * Sets the internationalized description of this task
	 * 
	 * @param aLocalizedString
	 *            The description to set.
	 */
	public void setDescription(final LocalizedString aLocalizedString) {
		this.aLocalizedStringDescription = aLocalizedString;
	}

	/**
	 * Adds a localized description to this task
	 * 
	 * @param sLang
	 *            the locale of the description
	 * @param sDesc
	 *            the localized description of this task
	 */
	public void addDescription(final String sLang, final String sDesc) {
		this.aLocalizedStringDescription.addLocalization(sLang, sDesc);
	}

	/**
	 * Gets the id of this task
	 * 
	 * @return Returns the id.
	 */
	public String getID() {
		return this.sID;
	}

	/**
	 * Sets the id of this task
	 * 
	 * @param sID
	 *            The id to set.
	 */
	public void setID(final String sID) {
		this.sID = sID;
	}

	/**
	 * Returns the itnernationalized long name of this task
	 * 
	 * @return Returns the longname.
	 */
	public LocalizedString getLongName() {
		return this.aLocalizedStringLongName;
	}

	/**
	 * Returns a localized long name of this task
	 * 
	 * @param sLang
	 *            requested locale (e.g. en, fr, zn-ch, ...)
	 * @return the localized long name corresponding to the locale
	 */
	public String getLongName(final String sLang) {
		final String sName = this.aLocalizedStringLongName
				.getLocalization(sLang);
		if (sName == null) {
			return "";
		}
		return sName;
	}

	/**
	 * Sets the internationalized long name of this task
	 * 
	 * @param aLocalizedString
	 *            The longname to set.
	 */
	public void setLongName(final LocalizedString aLocalizedString) {
		this.aLocalizedStringLongName = aLocalizedString;
	}

	/**
	 * Adds a localized long name to this task
	 * 
	 * @param sLang
	 *            the locale of the long name
	 * @param sLongName
	 *            the localized long name of this task
	 */
	public void addLongName(final String sLang, final String sLongName) {
		this.aLocalizedStringLongName.addLocalization(sLang, sLongName);
	}

	/**
	 * Returns the parameters list of this task
	 * 
	 * @return Returns the parameters.
	 */
	public Map<String, Parameter> getMapOfParameter() {
		return this.mapOfTaskParameter;
	}

	/**
	 * Sets the parameters list of this task
	 * 
	 * @param mapOfParameter
	 *            The parameters to set.
	 */
	public void setMapOfParameter(final Map<String, Parameter> mapOfParameter) {
		this.mapOfTaskParameter = mapOfParameter;
	}

	/**
	 * Adds a parameter to this task
	 * 
	 * @param aParameter
	 */
	public void addParameter(final Parameter aParameter) {
		this.mapOfTaskParameter.put(aParameter.getName(), aParameter);
	}


	/**
	 * Returns a list of tasknames referenced bye this task
	 * 
	 * @return Returns the references.
	 */
	public List<String> getListOfReference() {
		return this.listOfReference;
	}

	/**
	 * Recursively expands this task and referenced ones and merges observations
	 * and parameters.<br/> If a task A includes a task B that includes a task
	 * C, expand will put both B and C in A's referenced tasks.
	 * 
	 * @param mapOfTask
	 */
	/*
	public void expand(final Map<String, Task> mapOfTask) {
		this.bExpandingOrExpanded = true;
		final List<String> listOfOldReference = new ArrayList<String>();
		// re-ask why there build another list of reference
		// it's because he add reference in this.references
		for (final String sReference : this.listOfReference) {
			listOfOldReference.add(sReference);
		}

		for (final String sReference : listOfOldReference) {
			final Task aTask = mapOfTask.get(sReference);

			if (aTask == null) {
				Task.logger.error("The task " + sReference
						+ " directly referenced " + "by the task"
						+ this.getID() + " does not seem to"
						+ " exist... Ignoring reference");
				continue;
			}

			this.merge(aTask);

			if (!aTask.bExpandingOrExpanded) {
				aTask.expand(mapOfTask);
			}

			for (final String sNewReference : aTask.listOfReference) {
				if (this.listOfReference.contains(sNewReference)
						|| this.sID.equals(sNewReference)) {
					continue;
				}
				final Task aTaskCurrentRef = mapOfTask.get(sNewReference);
				if (aTaskCurrentRef == null) {
					Task.logger.error("The task " + sReference + " recursively"
							+ " referenced by the task" + getID()
							+ " does not seem to exist... Ignoring "
							+ "reference");
					continue;
				}
				this.listOfReference.add(sNewReference);
				this.merge(aTaskCurrentRef);
			}
		}
	}
	*/

	/**
	 * Merges another task with this one
	 * 
	 * @param aNotherTask
	 *            the task to merge
	 */
	/*
	private void merge(final Task aNotherTask) {
		this.mergeObservations(aNotherTask);
		this.mergeParameters(aNotherTask);
	}
*/
	/**
	 * Merges observations of another task with this one
	 * 
	 * @param aNotherTask
	 *            the task to merge
	 */
	/*
	private void mergeObservations(final Task aNotherTask) {
		Task.logger.trace("mergeObservations");
		if (Task.logger.isDebugEnabled()) {
			Task.logger.debug("Other task : " + aNotherTask + ".");
		}
		final Map<String, Observation> mapOfObservation = aNotherTask
				.getMapOfObservation();
		for (final String sObservationID : mapOfObservation.keySet()) {
			final Observation aObservation = mapOfObservation
					.get(sObservationID);
			if (this.mapOfObservation.containsKey(sObservationID)) {
				this.mapOfObservation.get(sObservationID).merge(aObservation);
			} else {
				this.mapOfObservation.put(sObservationID, aObservation);
			}
		}
	}
*/
	/**
	 * Merges parameters of another task with this one.
	 * 
	 * @param aNotherTask
	 *            the other task to merge
	 */
	private void mergeParameters(final Task aNotherTask) {
		final Map<String, Parameter> mapOfParameter = aNotherTask
				.getMapOfParameter();
		for (final String sParameterName : mapOfParameter.keySet()) {
			final Parameter aLocalParameter = this.mapOfTaskParameter
					.get(sParameterName);
			final Parameter aNotherParameter = mapOfParameter
					.get(sParameterName);
			if (aLocalParameter != null) {
				aLocalParameter.merge(aNotherParameter);
			} else {
				this.mapOfTaskParameter.put(sParameterName, aNotherParameter);
			}
		}
	}
	
	

	public void mergeSubtask(final Map<String, Task> mapOfTask, Task subtask) {
		for (TLTExec exec : subtask.getTree().getExecutionList()) {
			if (exec.getType().equals("observation"))
				this.root.addExec(exec);
			else if (exec.getType().equals("subtask")) {
				Task newTask = mapOfTask.get(exec.getValue());
				newTask.expandNode(mapOfTask,newTask.getTree());
				mergeSubtask(mapOfTask,newTask);
			}
				
		}
		for (TLTIf tltIf : subtask.getTree().getIfList()) {
			this.root.addIf(tltIf);
		}
	}
	
	/**
	 * 
	 */
	public TLTNode expandNode(final Map<String, Task> mapOfTask, TLTNode aRoot) {
		aRoot.bExpandingOrExpanded = true;
		
		TLTNode finalRoot = new TLTNode();
		
		for (TLTExec exec : aRoot.getExecutionList()) {
			if (exec.getType().equals("subtask")) {
				finalRoot = mergeNode(mapOfTask, finalRoot, 
						mapOfTask.get(exec.getValue()).getTree());
			}
			else if (exec.getType().equals("observation")) {
				finalRoot.addExec(exec);
			}
		}	

		for (TLTIf tltIf : aRoot.getIfList()) {
			tltIf = expandIf(mapOfTask, tltIf);
			finalRoot.addIf(tltIf);
		}
		
		return finalRoot;
	}
	
	
		public TLTNode mergeNode(final Map<String,Task> mapOfTask, TLTNode firstNode,
				TLTNode secondNode) {
			TLTNode finalNode = firstNode;
			for (TLTExec exec : secondNode.getExecutionList()) {
				if (exec.getType().equals("observation"))
					finalNode.addExec(exec);
				else if (exec.getType().equals("subtask")) {
					TLTNode newNode = mapOfTask.get(exec.getValue()).getTree();
					if (!mapOfTask.get(exec.getValue()).getTree().bExpandingOrExpanded) 
						newNode = 
							expandNode(mapOfTask,mapOfTask.get(exec.getValue()).getTree());					
					finalNode = mergeNode(mapOfTask,finalNode,newNode);
				}
			}
			for (TLTIf tltIf : secondNode.getIfList()) {
				tltIf = expandIf(mapOfTask,tltIf);
				finalNode.addIf(tltIf);
			}	
			return finalNode;
		}
		
	
		public TLTIf expandIf(final Map<String,Task> mapOfTask, TLTIf tltIf) {
			if (!tltIf.getIfOk().bExpandingOrExpanded) {
				TLTNode tltIfOk = expandNode(mapOfTask,tltIf.getIfOk());
				tltIf.setIfOk(tltIfOk);
			}
			if (!tltIf.getIfNotOk().bExpandingOrExpanded) {
				TLTNode tltIfNotOk = expandNode(mapOfTask,tltIf.getIfNotOk());	
				tltIf.setIfNotOk(tltIfNotOk);
			}
			return tltIf;
		}

	/**
	 * Adds a reference to another task
	 * 
	 * @param sReference
	 *            the referenced task
	 */
	public void addReference(final String sReference) {
		this.listOfReference.add(sReference);
	}

	public String toString() {
		final int iStringBufferSize = 5000;
		final String sVariableSeparator = "\n";
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);

		aStringBuffer.append("ID:").append(sID);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("parameters:").append(this.mapOfTaskParameter);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("references:").append(this.listOfReference);

		return aStringBuffer.toString();
	}


}
