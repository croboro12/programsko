package programsko;

import java.awt.List;

import javax.management.Attribute;

import weka.core.FastVector;
import weka.core.Instances;
import weka.filters.supervised.attribute.NominalToBinary;

public class ThresholdRad {
	private static Instances trainDataset,testDataset,noviDataset;
	private static int i,j;
	private static double vrijednost;
	private static FastVector lista = new FastVector();
	private static Izvjestaj prozor =new Izvjestaj();
	public static void manipuliraj(UcitajPodatke podaci) throws Exception {
		trainDataset = podaci.train;
		prozor.setVisible(true);
		lista.addElement("0");
		lista.addElement("1");
		weka.core.Attribute atribut = new  weka.core.Attribute("Classindeks",lista);
		trainDataset.insertAttributeAt(atribut, trainDataset.numAttributes());
		
		testDataset = podaci.test;
		testDataset.insertAttributeAt(atribut, testDataset.numAttributes()); // dodajemo attribut sa 0 i 1
		testDataset.setClassIndex(testDataset.numAttributes()-1); // dodajemo clas indeks na zadnji
		for(j = 0; j< testDataset.numInstances();j++) {
			vrijednost = testDataset.instance(j).value(testDataset.numAttributes()-2); // gledamo greske 
			//System.out.println(vrijednost);
			if(vrijednost > 0) {
				testDataset.instance(j).setClassValue(1); // ako da stavljamo u testu 1
			}else {
				testDataset.instance(j).setClassValue(0);
			}
			
		}
		for(j = 0; j < Guii.brojactresh; j++) {
			noviDataset = podaci.train;
			noviDataset.setClassIndex(noviDataset.numAttributes()-1);
			for(i = 0; i < trainDataset.numInstances(); i++){
				vrijednost = trainDataset.instance(i).value(trainDataset.numAttributes()-2);
				double klas = testDataset.instance(i).classValue();
				if(vrijednost > j) {
					noviDataset.instance(i).setValue(noviDataset.classAttribute(), 1); // 1 ako je greska veca od tresholda
				}else {
					noviDataset.instance(i).setValue(noviDataset.classAttribute(), 0);
				}
				Izvjestaj.Pisi(noviDataset.instance(i).classValue()+" Broj gresaka : "+vrijednost +" |clas vrijednost: "+klas +"\n");
				
				
			}
			Regresija.regresija(noviDataset,testDataset);
			Izvjestaj.Pisi("Gotov " + j +" krug");
		}
	}
		
}
