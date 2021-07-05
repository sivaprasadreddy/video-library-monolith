package com.sivalabs.videolibrary;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ArchTest {

    JavaClasses importedClasses =
            new ClassFileImporter()
                    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                    .importPackages("com.sivalabs.videolibrary");

    @Test
    void checkModuleBoundaries() {

        noClasses()
                .that()
                .resideInAnyPackage("com.sivalabs.videolibrary.common..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "com.sivalabs.videolibrary.config..",
                        "com.sivalabs.videolibrary.catalog..",
                        "com.sivalabs.videolibrary.customers..",
                        "com.sivalabs.videolibrary.orders..")
                .because("Common classes should not depend on other modules")
                .check(importedClasses);

        noClasses()
                .that()
                .resideInAnyPackage("com.sivalabs.videolibrary.catalog..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "com.sivalabs.videolibrary.config..",
                        "com.sivalabs.videolibrary.customers..",
                        "com.sivalabs.videolibrary.orders..")
                .because("Catalog classes should not depend on config, catalog, order modules")
                .check(importedClasses);

        noClasses()
                .that()
                .resideInAnyPackage("com.sivalabs.videolibrary.customers..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "com.sivalabs.videolibrary.config..",
                        "com.sivalabs.videolibrary.catalog..",
                        "com.sivalabs.videolibrary.orders..")
                .because("Customers classes should not depend on config, catalog, order modules")
                .check(importedClasses);

        noClasses()
                .that()
                .resideInAnyPackage("com.sivalabs.videolibrary.orders..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("com.sivalabs.videolibrary.config..")
                .because("Orders classes should not depend on config module")
                .check(importedClasses);
    }

    @Test
    void shouldNotUseFieldInjection() {
        noFields().should().beAnnotatedWith(Autowired.class).check(importedClasses);
    }

    @Test
    void shouldNotUseJunit4Classes() {

        JavaClasses classes = new ClassFileImporter().importPackages("com.sivalabs.videolibrary");

        noClasses()
                .should()
                .accessClassesThat()
                .resideInAnyPackage("org.junit")
                .because("Tests should use Junit5 instead of Junit4")
                .check(classes);

        noMethods()
                .should()
                .beAnnotatedWith("org.junit.Test")
                .orShould()
                .beAnnotatedWith("org.junit.Ignore")
                .because("Tests should use Junit5 instead of Junit4")
                .check(classes);
    }
}
