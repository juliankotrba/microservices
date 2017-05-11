package org.xine.paginator.business.registrations.boundary;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.xine.paginator.business.registrations.control.VatCalculator;
import org.xine.paginator.business.registrations.entity.Registration;

public class RegistrationsTest {

    Registrations cut;

    @Before
    public void init() {
        this.cut = new Registrations();
        this.cut.priceCalculator = mock(VatCalculator.class);
        this.cut.em = mock(EntityManager.class);
        this.cut.tracer = mock(Logger.class);
    }

    void mockQuery(final String name, final List<Registration> results) {
        final Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(results);
        when(mockedQuery.setParameter(Matchers.anyString(), Matchers.anyObject())).thenReturn(mockedQuery);
        when(this.cut.em.createNamedQuery(name)).thenReturn(mockedQuery);
    }

    @Test
    public void successfulRegistration() {
        when(this.cut.priceCalculator.calculateTotal(Matchers.anyBoolean(), Matchers.anyInt())).thenReturn(1);
        final Registration registration = new Registration(true, 1, 1);
        merge(registration);
        this.cut.register(registration);
    }

    @Test
    public void convertEmptyListToJson() {
        mockQuery(Registration.findAll, Collections.EMPTY_LIST);
        final JsonArray result = this.cut.allAsJson();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void convertFilledListToJson() {
        final List<Registration> registrations = new ArrayList<>();
        final Registration expected = mock(Registration.class);
        when(expected.getId()).thenReturn(42l);
        registrations.add(expected);
        mockQuery(Registration.findAll, registrations);
        final JsonArray result = this.cut.allAsJson();
        assertNotNull(result);
        assertThat(result.size(), is(1));
        final JsonObject actual = result.getJsonObject(0);
        final JsonNumber actualId = actual.getJsonNumber(Registrations.CONFIRMATION_ID);
        assertThat(expected.getId(), is(actualId.longValue()));

    }

    void merge(final Registration registration) {
        when(this.cut.em.merge(registration)).thenReturn(registration);
    }

}
