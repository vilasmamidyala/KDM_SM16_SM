package ontInterface;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class OwlPizza {
    OWLOntology ont;
    PrefixManager pm;
    OWLOntologyManager manager;
    OWLDataFactory df;

    public OwlPizza() {
        try {
            pm = new DefaultPrefixManager(null, null, "https://www.kdm.com/OWL/Election#");
            manager = OWLManager.createOWLOntologyManager();
            df = manager.getOWLDataFactory();
            ont = initialzation();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createSubClass(String className, String subClassName) {
        OWLClass baseClass = df.getOWLClass(className, pm);
        OWLClass subClass = df.getOWLClass(subClassName, pm);
        OWLSubClassOfAxiom declarationSubClassAxiom = df.getOWLSubClassOfAxiom(subClass, baseClass);
        manager.addAxiom(ont, declarationSubClassAxiom);
    }

    public void createClass(String className) {

        OWLClass classN = df.getOWLClass(className, pm);
        OWLDeclarationAxiom declarationAxiom = df.getOWLDeclarationAxiom(classN);
        manager.addAxiom(ont, declarationAxiom);

    }

    public void createIndividual(String individualName, String className) {
        OWLClass classN = df.getOWLClass(className, pm);
        OWLNamedIndividual ind = df.getOWLNamedIndividual(individualName, pm);
        OWLClassAssertionAxiom classAssertion = df.getOWLClassAssertionAxiom(classN, ind);
        manager.addAxiom(ont, classAssertion);

    }

    public void createObjectProperty(String domain, String propertyName, String range) {

        OWLClass domainC = df.getOWLClass(domain, pm);
        OWLClass rangeC = df.getOWLClass(range, pm);
        OWLObjectProperty isIngredientOf = df.getOWLObjectProperty(propertyName, pm);
        OWLObjectPropertyRangeAxiom rangeAxiom = df.getOWLObjectPropertyRangeAxiom(isIngredientOf, rangeC);
        OWLObjectPropertyDomainAxiom domainAxiom = df.getOWLObjectPropertyDomainAxiom(isIngredientOf, domainC);
        manager.addAxiom(ont, rangeAxiom);
        manager.addAxiom(ont, domainAxiom);

    }

    public OWLOntology initialzation() throws Exception {
        //creating ontology manager
        //In order to create objects that represent entities we need a

        ont = manager.createOntology(IRI.create("https://www.kdm.com/OWL/", "Election"));
        OWLClass Pizza = df.getOWLClass(":Election", pm);
        OWLClass PizzaTopping = df.getOWLClass(":USA_Elections", pm);
        OWLClass PizzaBase = df.getOWLClass(":USA_Parties", pm);

        OWLDeclarationAxiom declarationAxiomPizza = df.getOWLDeclarationAxiom(Pizza);
        OWLDeclarationAxiom declarationAxiomPizzaTopping = df.getOWLDeclarationAxiom(PizzaTopping);
        OWLDeclarationAxiom declarationAxiomPizzaBase = df.getOWLDeclarationAxiom(PizzaBase);

        manager.addAxiom(ont, declarationAxiomPizza);
        manager.addAxiom(ont, declarationAxiomPizzaTopping);
        manager.addAxiom(ont, declarationAxiomPizzaBase);

        //Making all classes Disjoint to each other
        OWLDisjointClassesAxiom disjointClassesAxiom = df.getOWLDisjointClassesAxiom(Pizza, PizzaTopping, PizzaBase);
        manager.addAxiom(ont, disjointClassesAxiom);

        //Creating Subclasses for PizzaBase class
        OWLClass ThinAndCrisyBase = df.getOWLClass(":Democratic", pm);
        OWLClass DeepPanBase = df.getOWLClass(":Republic", pm);
        OWLSubClassOfAxiom declarationAxiomThinAndCrisyBase = df.getOWLSubClassOfAxiom(ThinAndCrisyBase, PizzaBase);
        OWLSubClassOfAxiom declarationAxiomDeepPanBase = df.getOWLSubClassOfAxiom(DeepPanBase, PizzaBase);
        manager.addAxiom(ont, declarationAxiomThinAndCrisyBase);
        manager.addAxiom(ont, declarationAxiomDeepPanBase);

        //Creating Subclasses for PizzaTopping class
        OWLClass MeatTopping = df.getOWLClass(":States", pm);
        OWLClass VegetableTopping = df.getOWLClass(":Senators", pm);
        OWLClass CheeseTopping = df.getOWLClass(":HillaryClinton", pm);
        OWLClass SeafoodTopping = df.getOWLClass(":DonaldTrump", pm);
        OWLSubClassOfAxiom declarationAxiomMeatTopping = df.getOWLSubClassOfAxiom(MeatTopping, PizzaTopping);
        OWLSubClassOfAxiom declarationAxiomVegetableTopping = df.getOWLSubClassOfAxiom(VegetableTopping, PizzaTopping);
        OWLSubClassOfAxiom declarationAxiomCheeseTopping = df.getOWLSubClassOfAxiom(CheeseTopping, PizzaTopping);
        OWLSubClassOfAxiom declarationAxiomSeafoodTopping = df.getOWLSubClassOfAxiom(SeafoodTopping, PizzaTopping);
        manager.addAxiom(ont, declarationAxiomMeatTopping);
        manager.addAxiom(ont, declarationAxiomVegetableTopping);
        manager.addAxiom(ont, declarationAxiomCheeseTopping);
        manager.addAxiom(ont, declarationAxiomSeafoodTopping);
/*
        //Creating Subclasses for MeatTopping class
        OWLClass SpicyBeefTopping = df.getOWLClass(":Campaign", pm);
        OWLClass PepperoniTopping = df.getOWLClass(":Poll", pm);
        OWLClass SalamiTopping = df.getOWLClass(":Sufferage", pm);
        OWLClass HamTopping = df.getOWLClass(":ballot", pm);
        OWLSubClassOfAxiom declarationAxiomSpicyBeefTopping = df.getOWLSubClassOfAxiom(SpicyBeefTopping, MeatTopping);
        OWLSubClassOfAxiom declarationAxiomPepperoniTopping = df.getOWLSubClassOfAxiom(PepperoniTopping, MeatTopping);
        OWLSubClassOfAxiom declarationAxiomSalamiTopping = df.getOWLSubClassOfAxiom(SalamiTopping, MeatTopping);
        OWLSubClassOfAxiom declarationAxiomHamTopping = df.getOWLSubClassOfAxiom(HamTopping, MeatTopping);
        manager.addAxiom(ont, declarationAxiomSpicyBeefTopping);
        manager.addAxiom(ont, declarationAxiomPepperoniTopping);
        manager.addAxiom(ont, declarationAxiomSalamiTopping);
        manager.addAxiom(ont, declarationAxiomHamTopping);

        //Creating Subclasses for VegetableTopping class
        OWLClass TomatoTopping = df.getOWLClass(":candidate", pm);
        OWLClass OliveTopping = df.getOWLClass(":congress", pm);
        OWLClass MushroomTopping = df.getOWLClass(":Republican Party", pm);
        OWLClass PepperTopping = df.getOWLClass(":democracy", pm);
        OWLClass OnionTopping = df.getOWLClass(":precinct", pm);
        OWLClass CaperTopping = df.getOWLClass(":referendum", pm);
        OWLSubClassOfAxiom declarationAxiomSpicyTomatoTopping = df.getOWLSubClassOfAxiom(TomatoTopping, VegetableTopping);
        OWLSubClassOfAxiom declarationAxiomOliveTopping = df.getOWLSubClassOfAxiom(OliveTopping, VegetableTopping);
        OWLSubClassOfAxiom declarationAxiomMushroomTopping = df.getOWLSubClassOfAxiom(MushroomTopping, VegetableTopping);
        OWLSubClassOfAxiom declarationAxiomPepperTopping = df.getOWLSubClassOfAxiom(PepperTopping, VegetableTopping);
        OWLSubClassOfAxiom declarationAxiomOnionTopping = df.getOWLSubClassOfAxiom(OnionTopping, VegetableTopping);
        OWLSubClassOfAxiom declarationAxiomCaperTopping = df.getOWLSubClassOfAxiom(CaperTopping, VegetableTopping);
        manager.addAxiom(ont, declarationAxiomSpicyTomatoTopping);
        manager.addAxiom(ont, declarationAxiomOliveTopping);
        manager.addAxiom(ont, declarationAxiomMushroomTopping);
        manager.addAxiom(ont, declarationAxiomPepperTopping);
        manager.addAxiom(ont, declarationAxiomOnionTopping);
        manager.addAxiom(ont, declarationAxiomCaperTopping);

        //Creating Subclasses for PepperTopping class
        OWLClass RedPepperTopping = df.getOWLClass(":bipartisan", pm);
        OWLClass GreenPepperTopping = df.getOWLClass(":bicameral", pm);
        OWLClass JalapenoPepperTopping = df.getOWLClass(":campaign", pm);
        OWLSubClassOfAxiom declarationAxiomRedPepperTopping = df.getOWLSubClassOfAxiom(RedPepperTopping, PepperTopping);
        OWLSubClassOfAxiom declarationAxiomGreenPepperTopping = df.getOWLSubClassOfAxiom(GreenPepperTopping, PepperTopping);
        OWLSubClassOfAxiom declarationAxiomJalapenoPepperTopping = df.getOWLSubClassOfAxiom(JalapenoPepperTopping, PepperTopping);
        manager.addAxiom(ont, declarationAxiomRedPepperTopping);
        manager.addAxiom(ont, declarationAxiomGreenPepperTopping);
        manager.addAxiom(ont, declarationAxiomJalapenoPepperTopping);
*/
        //Creating Subclasses for CheeseTopping class
        OWLClass MozzarellaTopping = df.getOWLClass(":FemaleCandidate", pm);
        OWLClass ParmezanTopping = df.getOWLClass(":Democratic", pm);
        OWLSubClassOfAxiom declarationAxiomGreenMozzarellaTopping = df.getOWLSubClassOfAxiom(MozzarellaTopping, CheeseTopping);
        OWLSubClassOfAxiom declarationAxiomJalapenoParmezanTopping = df.getOWLSubClassOfAxiom(ParmezanTopping, CheeseTopping);
        manager.addAxiom(ont, declarationAxiomGreenMozzarellaTopping);
        manager.addAxiom(ont, declarationAxiomJalapenoParmezanTopping);

        //Creating Subclasses for SeafoodTopping class
        OWLClass TunaTopping = df.getOWLClass(":Republic", pm);
        OWLClass AnchovyTopping = df.getOWLClass(":RealEstate", pm);
        OWLClass PrawnTopping = df.getOWLClass(":businessman", pm);
        OWLSubClassOfAxiom declarationAxiomRedTunaTopping = df.getOWLSubClassOfAxiom(TunaTopping, SeafoodTopping);
        OWLSubClassOfAxiom declarationAxiomAnchovyTopping = df.getOWLSubClassOfAxiom(AnchovyTopping, SeafoodTopping);
        OWLSubClassOfAxiom declarationAxiomPrawnTopping = df.getOWLSubClassOfAxiom(PrawnTopping, SeafoodTopping);
        manager.addAxiom(ont, declarationAxiomRedTunaTopping);
        manager.addAxiom(ont, declarationAxiomAnchovyTopping);
        manager.addAxiom(ont, declarationAxiomPrawnTopping);

        //Creating Class Country and Individuals to classes
        OWLClass Country = df.getOWLClass(":Congress", pm);
        OWLDeclarationAxiom declarationAxiomCountry = df.getOWLDeclarationAxiom(Country);
        OWLNamedIndividual India = df.getOWLNamedIndividual(":Bipartisian", pm);
        OWLNamedIndividual USA = df.getOWLNamedIndividual(":bicameral", pm);
        OWLNamedIndividual UK = df.getOWLNamedIndividual(":Campaign", pm);
        //Class Assertion specifying India is member of Class Country
        OWLClassAssertionAxiom classAssertionIndia = df.getOWLClassAssertionAxiom(Country, India);
        OWLClassAssertionAxiom classAssertionUSA = df.getOWLClassAssertionAxiom(Country, USA);
        OWLClassAssertionAxiom classAssertionUK = df.getOWLClassAssertionAxiom(Country, UK);
        manager.addAxiom(ont, declarationAxiomCountry);
        manager.addAxiom(ont, classAssertionIndia);
        manager.addAxiom(ont, classAssertionUSA);
        manager.addAxiom(ont, classAssertionUK);

        //Creating Food class
        OWLClass Food = df.getOWLClass(":Election", pm);
        OWLDeclarationAxiom declarationAxiomFood = df.getOWLDeclarationAxiom(Food);
        manager.addAxiom(ont, declarationAxiomFood);

        //Creating Object Properties
        OWLObjectProperty isIngredientOf = df.getOWLObjectProperty(":has", pm);
        OWLObjectPropertyRangeAxiom rangeAxiomisIngredientOf = df.getOWLObjectPropertyRangeAxiom(isIngredientOf, Food);
        OWLObjectPropertyDomainAxiom domainAxiomisIngredientOf = df.getOWLObjectPropertyDomainAxiom(isIngredientOf, Food);
        manager.addAxiom(ont, rangeAxiomisIngredientOf);
        manager.addAxiom(ont, domainAxiomisIngredientOf);

        OWLObjectProperty hasIngredient = df.getOWLObjectProperty(":consists", pm);
        OWLObjectPropertyRangeAxiom rangeAxiomhasIngredient = df.getOWLObjectPropertyRangeAxiom(hasIngredient, Food);
        OWLObjectPropertyDomainAxiom domainAxiomhasIngredient = df.getOWLObjectPropertyDomainAxiom(hasIngredient, Food);
        manager.addAxiom(ont, rangeAxiomhasIngredient);
        manager.addAxiom(ont, domainAxiomhasIngredient);

        //Making isIngredientOf and hasIngredient inverse properties
        manager.addAxiom(ont, df.getOWLInverseObjectPropertiesAxiom(isIngredientOf, hasIngredient));

        //Creating hasTopping, hasBase Properties
        OWLObjectProperty hasTopping = df.getOWLObjectProperty(":hasrelation", pm);
        OWLObjectPropertyDomainAxiom domainAxiomhasTopping = df.getOWLObjectPropertyDomainAxiom(hasTopping, Pizza);
        OWLObjectPropertyRangeAxiom rangeAxiomhasTopping = df.getOWLObjectPropertyRangeAxiom(hasTopping, PizzaTopping);
        manager.addAxiom(ont, rangeAxiomhasTopping);
        manager.addAxiom(ont, domainAxiomhasTopping);
        manager.addAxiom(ont, df.getOWLSubObjectPropertyOfAxiom(hasTopping, hasIngredient));

        OWLObjectProperty hasBase = df.getOWLObjectProperty(":having", pm);
        OWLObjectPropertyDomainAxiom domainAxiomhasBase = df.getOWLObjectPropertyDomainAxiom(hasBase, Pizza);
        OWLObjectPropertyRangeAxiom rangeAxiomhasBase = df.getOWLObjectPropertyRangeAxiom(hasBase, PizzaBase);
        manager.addAxiom(ont, rangeAxiomhasBase);
        manager.addAxiom(ont, domainAxiomhasBase);
        manager.addAxiom(ont, df.getOWLSubObjectPropertyOfAxiom(hasBase, hasIngredient));

        //Making hasBase property as Functional
        manager.addAxiom(ont, df.getOWLFunctionalObjectPropertyAxiom(hasBase));

        //Making hasBase property as Transitive
        manager.addAxiom(ont, df.getOWLTransitiveObjectPropertyAxiom(hasIngredient));

        //Creating isToppingOf, isBaseOf Properties
        OWLObjectProperty isToppingOf = df.getOWLObjectProperty(":isrelatedto", pm);
        OWLObjectPropertyDomainAxiom domainAxiomisToppingOf = df.getOWLObjectPropertyDomainAxiom(isToppingOf, PizzaTopping);
        OWLObjectPropertyRangeAxiom rangeAxiomisToppingOf = df.getOWLObjectPropertyRangeAxiom(isToppingOf, Pizza);
        manager.addAxiom(ont, domainAxiomisToppingOf);
        manager.addAxiom(ont, rangeAxiomisToppingOf);
        manager.addAxiom(ont, df.getOWLSubObjectPropertyOfAxiom(isToppingOf, isIngredientOf));

        OWLObjectProperty isBaseOf = df.getOWLObjectProperty(":isrelatedwith", pm);
        OWLObjectPropertyDomainAxiom domainAxiomisBaseOf = df.getOWLObjectPropertyDomainAxiom(isBaseOf, PizzaBase);
        OWLObjectPropertyRangeAxiom rangeAxiomisBaseOf = df.getOWLObjectPropertyRangeAxiom(isBaseOf, Pizza);
        manager.addAxiom(ont, domainAxiomisBaseOf);
        manager.addAxiom(ont, rangeAxiomisBaseOf);
        manager.addAxiom(ont, df.getOWLSubObjectPropertyOfAxiom(isBaseOf, isIngredientOf));


        //Making isToppingOf and hasTopping inverse properties
        manager.addAxiom(ont, df.getOWLInverseObjectPropertiesAxiom(isToppingOf, hasTopping));

        //Making isBaseOf and hasBase inverse properties
        manager.addAxiom(ont, df.getOWLInverseObjectPropertiesAxiom(isBaseOf, hasBase));


        //Creating Data property
        OWLDataProperty hasVarieties = df.getOWLDataProperty(":hasDifferent", pm);
        OWLDatatype integerDatatype = df.getIntegerOWLDatatype();
        OWLDataPropertyDomainAxiom domainAxiomhasVarieties = df.getOWLDataPropertyDomainAxiom(hasVarieties, Country);
        OWLDataPropertyRangeAxiom rangeAxiomhasVarieties = df.getOWLDataPropertyRangeAxiom(hasVarieties, integerDatatype);
        manager.addAxiom(ont, domainAxiomhasVarieties);
        manager.addAxiom(ont, rangeAxiomhasVarieties);

        //Some values from Restriction
        OWLClassExpression hasBaseRestriction = df.getOWLObjectSomeValuesFrom(hasBase, PizzaBase);
        OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(Pizza, hasBaseRestriction);
        manager.addAxiom(ont, ax);

        //Creating different kind of pizzas
        OWLClass NamedPizza = df.getOWLClass(":Party", pm);
        OWLSubClassOfAxiom declarationAxiomNamedPizza = df.getOWLSubClassOfAxiom(NamedPizza, Pizza);
        manager.addAxiom(ont, declarationAxiomNamedPizza);

        OWLClass MargheritaPizza = df.getOWLClass(":Election", pm);
        OWLSubClassOfAxiom declarationAxiomMargheritaPizza = df.getOWLSubClassOfAxiom(MargheritaPizza, NamedPizza);
        manager.addAxiom(ont, declarationAxiomMargheritaPizza);

        OWLAnnotation commentAnno = df.getRDFSComment(df.getOWLLiteral("America Elections : democracy and republic.  Who will win", "en"));
        OWLAxiom commentMargheritaPizza = df.getOWLAnnotationAssertionAxiom(MargheritaPizza.getIRI(), commentAnno);
        manager.addAxiom(ont, commentMargheritaPizza);

        OWLClassExpression hasToppingRestriction = df.getOWLObjectSomeValuesFrom(hasTopping, MozzarellaTopping);
        OWLSubClassOfAxiom axiomhasToppingRestriction = df.getOWLSubClassOfAxiom(MargheritaPizza, hasToppingRestriction);
        manager.addAxiom(ont, axiomhasToppingRestriction);
/*
        OWLClassExpression hasToppingRestrictionTomato = df.getOWLObjectSomeValuesFrom(hasTopping, TomatoTopping);
        OWLSubClassOfAxiom axiomhasToppingRestrictionTomato = df.getOWLSubClassOfAxiom(MargheritaPizza, hasToppingRestrictionTomato);
        manager.addAxiom(ont, axiomhasToppingRestrictionTomato);
*/
        return ont;
    }

    public void saveOntology() {
        try {
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OutputStream os = new FileOutputStream("data/Election2.owl");
            OWLXMLDocumentFormat owlxmlFormat = new OWLXMLDocumentFormat();
            manager.saveOntology(ont, owlxmlFormat, os);
            System.out.println("Ontology Created");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
