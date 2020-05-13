/**
 * Copyright (c) 2019 itemis AG and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *   Andreas Graf, itemis AG - 01.05.2020 Created
 */

/**
 * This file is a standalone demo of the Artop Framework for generation of Artop Models.
 * 
 * To run it, you need to install Artop in your taget platform from www.artop.org
 * 
 * Artop is only available to companies that are an AUTOSAR member. 
 * 
 * Code is only for informational purposes.
 * 
 */
package com.demo.itemis.webinar.artop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.artop.aal.common.resource.impl.AutosarResourceFactoryImpl;
import org.artop.aal.common.resource.impl.AutosarResourceSetImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;

import autosar21.swcomponent.datatype.valuemodel.V;
import autosar40.autosartoplevelstructure.AUTOSAR;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import autosar40.swcomponent.components.ApplicationSwComponentType;
import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.portinterface.SenderReceiverInterface;
import autosar40.util.Autosar40Factory;
import autosar40.util.Autosar40Package;
import autosar40.util.Autosar40ResourceFactoryImpl;

public class ArtopDemo {

	SenderReceiverInterface senderReceiverInterface = null;
	
	public void setup() {
		@SuppressWarnings("unused")
		Autosar40Package einstance = Autosar40Package.eINSTANCE;
	
		
		AutosarResourceFactoryImpl resourceFactory = new Autosar40ResourceFactoryImpl();

		// register
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("arxml", resourceFactory);
		
	}
	
	public AUTOSAR createFileContent1() {
		AUTOSAR autosar = Autosar40Factory.eINSTANCE.createAUTOSAR();
		
		 ARPackage arPackage = Autosar40Factory.eINSTANCE.createARPackage(); 
		 arPackage.setShortName("pack1");
		 autosar.getArPackages().add(arPackage);
		 
		 senderReceiverInterface = Autosar40Factory.eINSTANCE.createSenderReceiverInterface();
		 senderReceiverInterface.setShortName("pedestrianList");
		 arPackage.getElements().add(senderReceiverInterface);
		 		
		 
		return autosar;
	}
	
	public AUTOSAR createFileContent2(SenderReceiverInterface senderReceiverInterface) {
		AUTOSAR autosar = Autosar40Factory.eINSTANCE.createAUTOSAR();
		
		 ARPackage arPackage = Autosar40Factory.eINSTANCE.createARPackage(); 
		 arPackage.setShortName("pack2");
		 autosar.getArPackages().add(arPackage);
		 
		ApplicationSwComponentType swComponentType = Autosar40Factory.eINSTANCE.createApplicationSwComponentType();
		swComponentType.setShortName("pedestrianDetector");
		 arPackage.getElements().add(swComponentType);
		 		
		 PPortPrototype portPrototype = Autosar40Factory.eINSTANCE.createPPortPrototype();
		 portPrototype.setShortName("detectedPedestrians");
		 portPrototype.setProvidedInterface(senderReceiverInterface);
		 swComponentType.getPorts().add(portPrototype);
		 
		return autosar;
	}
	
	
	public void execute() throws IOException {
		AutosarResourceSetImpl resourceSet = new AutosarResourceSetImpl();
		Map options = new HashMap();
		
		
		Resource resource = resourceSet.createResource(URI.createURI("file1.arxml"));
		AUTOSAR fileContent1 = createFileContent1();
		resource.getContents().add( fileContent1);
		
		Resource resource2 = resourceSet.createResource(URI.createURI("file2.arxml"));
		AUTOSAR fileContent2 = createFileContent2(senderReceiverInterface);
		resource2.getContents().add( fileContent2);
		
		resource.save(options);
		resource2.save(options);
	}
	
	public static void main(String[] args) throws IOException {
		ArtopDemo artopDemo = new ArtopDemo();
		artopDemo.setup();
		artopDemo.execute();
		
		
		
	}

	
}
