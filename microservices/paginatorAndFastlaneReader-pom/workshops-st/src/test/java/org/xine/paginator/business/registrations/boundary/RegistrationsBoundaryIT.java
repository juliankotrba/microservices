package org.xine.paginator.business.registrations.boundary;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

public class RegistrationsBoundaryIT {

    private static final String RUT_URI = "http://localhost:8080/workshops/resources/registrations";

    //Resource Under Test
    private WebTarget rut;
    private Client client;

    @Before
    public void initClient() {
        this.client = ClientBuilder.newBuilder().
                build();
        this.rut = this.client.target(RUT_URI);
    }

    @Test
    public void dummy() {
        final long originId = 42;
        final Registration actual = this.rut.path("/{id}/dummy").
                resolveTemplate("id", originId).
                request().
                accept(MediaType.APPLICATION_XML).
                get(Registration.class);
        assertNotNull(actual);
        final long actualId = actual.getId();
        assertThat(actualId, is(originId));
    }

    @Test
    public void crud() {
        final Registration in = new Registration(true, 1, 2);
        final Response response = this.rut.request(MediaType.APPLICATION_JSON).
                post(Entity.entity(in, MediaType.APPLICATION_XML));
        assertThat(response.getStatus(), is(201));
        final URI location = response.getLocation();
        assertNotNull(location);
        //post / creation
        final JsonObject out = this.client.target(location).
                request().
                accept(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        assertThat(out.getBoolean("vatIdAvailable"), is(in.isVatIdAvailable()));
        assertThat(out.getInt("numberOfDays"), is(in.getNumberOfDays()));
        assertThat(out.getInt("numberOfAttendees"), is(in.getNumberOfAttendees()));
        //all
        final JsonArray result = this.rut.request().
                accept(MediaType.APPLICATION_JSON).
                get(JsonArray.class);
        assertNotNull(result);
        assertTrue(result.size() > 0);

    }

}
