/*
 * Komunumo – Open Source Community Manager
 * Copyright (C) 2017 Java User Group Switzerland
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ch.jug.komunumo.business.sponsor.boundary;

import ch.jug.komunumo.business.sponsor.control.SponsorService;
import ch.jug.komunumo.business.sponsor.entity.Level;
import ch.jug.komunumo.business.sponsor.entity.Sponsor;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.net.URI;
import java.util.List;

@Path("sponsors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SponsorsResource {

    private final SponsorService service;

    @Inject
    public SponsorsResource(final SponsorService service) {
        this.service = service;
    }

    @POST
    public Response createSponsor(@Valid final Sponsor sponsor, @Context final UriInfo info) {
        final String id = this.service.create(sponsor).getId();
        final URI uri = info.getAbsolutePathBuilder().path(File.separator + id).build();
        return Response.created(uri).build();
    }

    @GET
    public List<Sponsor> readAllSponsors() {
        return this.service.readAll();
    }

    @GET
    @Path("active")
    public List<Sponsor> readActiveSponsors() {
        return this.service.readActive();
    }

    @GET
    @Path("{level}")
    public List<Sponsor> readSponsorsWithLevel(@PathParam("level") final String level) {
        return this.service.readWithLevel(Level.fromString(level));
    }

    @GET
    @Path("{level}/active")
    public List<Sponsor> readActiveSponsorsWithLevel(@PathParam("level") final String level) {
        return this.service.readActiveWithLevel(Level.fromString(level));
    }

    @PUT
    @Path("{id}")
    public Sponsor updateSponsor(@PathParam("id") final String id, @Valid final Sponsor sponsor, @Context final UriInfo info) {
        final Sponsor sponsorToUpdate = sponsor.toBuilder()
                .id(id)
                .build();
        return this.service.update(sponsorToUpdate);
    }

}
