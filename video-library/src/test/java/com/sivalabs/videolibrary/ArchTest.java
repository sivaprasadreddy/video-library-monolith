package com.sivalabs.videolibrary;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

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
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        noClasses()
                .that()
                .resideInAnyPackage("com.sivalabs.videolibrary.core.service..")
                .or()
                .resideInAnyPackage("com.sivalabs.videolibrary.core.repository..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("com.sivalabs.videolibrary.catalog.web..")
                .because("Services and repositories should not depend on web layer")
                .check(importedClasses);
    }

    @Test
    void shouldNotUseFieldInjection() {
        noFields().should().beAnnotatedWith(Autowired.class).check(importedClasses);
    }

    @Test
    void shouldFollowLayeredArchitecture() {
        layeredArchitecture()
                .layer("Config")
                .definedBy("..config..")
                .layer("Jobs")
                .definedBy("..jobs..")
                .layer("Importer")
                .definedBy("..importer..")
                .layer("Web")
                .definedBy("..web..")
                .layer("Service")
                .definedBy("..service..")
                .layer("Persistence")
                .definedBy("..repository..")
                .whereLayer("Web")
                .mayNotBeAccessedByAnyLayer()
                .whereLayer("Service")
                .mayOnlyBeAccessedByLayers("Config", "Importer", "Jobs", "Web")
                .whereLayer("Persistence")
                .mayOnlyBeAccessedByLayers("Service")
                .check(importedClasses);
    }

    @Test
    void shouldFollowNamingConvention() {
        classes()
                .that()
                .resideInAPackage("com.sivalabs.videolibrary.core.repository")
                .should()
                .haveSimpleNameEndingWith("Repository")
                .check(importedClasses);

        classes()
                .that()
                .resideInAPackage("com.sivalabs.videolibrary.core.service")
                .should()
                .haveSimpleNameEndingWith("Service")
                .check(importedClasses);
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
