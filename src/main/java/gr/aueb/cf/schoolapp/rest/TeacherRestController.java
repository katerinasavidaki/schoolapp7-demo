package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotAuthorizedException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dto.*;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.validator.ValidatorUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Path("/teachers")
public class TeacherRestController {

    //@Inject
    private final ITeacherService teacherService;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeacher(TeacherInsertDTO insertDTO, @Context UriInfo uriInfo)    //  injects the current request’s URI information
            throws EntityInvalidArgumentException, EntityAlreadyExistsException {
        List<String> errors = ValidatorUtil.validateDTO(insertDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentException("Teacher", String.join("\n", errors));
        }
        TeacherReadOnlyDTO readOnlyDTO = teacherService.insertTeacher(insertDTO);

        // Build the URI for the new resource
        URI newResourceUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(readOnlyDTO.id()))
                .build();
        return Response.created(newResourceUri).entity(readOnlyDTO).build();

//        return Response.created(uriInfo.getAbsolutePathBuilder().path(readOnlyDTO.getId().toString()).build())
//                .entity(readOnlyDTO)
//                .build();
    }

    @PUT
    @Path("/{teacherId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeacher(@PathParam("teacherId") Long teacherId,
                                  TeacherUpdateDTO updateDTO)
            throws EntityInvalidArgumentException, EntityNotFoundException, EntityNotAuthorizedException {

//        if (!Objects.equals(updateDTO.getId(), teacherId)) {
//            throw new EntityNotAuthorizedException("Teacher", "Not authorized to update id: " + teacherId);
//        }
        List<String> errors = ValidatorUtil.validateDTO(updateDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentException("Teacher", String.join(", ", errors));
        }

        TeacherReadOnlyDTO readOnlyDTO = teacherService.updateTeacher(updateDTO);
        return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
    }

    @DELETE
    @Path("/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeacher(@PathParam("teacherId") Long teacherId)
            throws EntityNotFoundException, EntityNotAuthorizedException {

        TeacherReadOnlyDTO dto = teacherService.getTeacherById(teacherId);
//        if (!Objects.equals(dto.getId(), teacherId)) {
//            throw new EntityNotAuthorizedException("Teacher", "Not authorized to update id: " + teacherId);
//        }
        teacherService.deleteTeacher(teacherId);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    @GET
    @Path("/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher(@PathParam("teacherId") Long id,
                               @Context SecurityContext securityContext)
            throws EntityNotFoundException, EntityNotAuthorizedException {

//        if (!securityContext.isUserInRole("TEACHER")) {
////            return Response.status(Response.Status.UNAUTHORIZED).build();
//            throw new EntityNotAuthorizedException("Teacher", "not authorized to get teacher id: " + id);
//        }
        TeacherReadOnlyDTO dto = teacherService.getTeacherById(id);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFiltered(@QueryParam("firstname") @DefaultValue("") String firstname,
                                @QueryParam("lastname") @DefaultValue("") String lastname,
                                @QueryParam("vat") @DefaultValue("") String vat) {

        TeacherFiltersDTO filtersDTO = new TeacherFiltersDTO(firstname, lastname, vat);
        Map<String, Object> criteria;
        criteria = Mapper.mapToCriteria(filtersDTO);
        List<TeacherReadOnlyDTO> readOnlyDTOS = teacherService.getTeachersByCriteria(criteria);
        return Response.status(Response.Status.OK).entity(readOnlyDTOS).build();
    }

    @GET
    @Path("/filtered")
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedResult<TeacherReadOnlyDTO> getFilteredPaginated(
            @QueryParam("firstname") @DefaultValue("") String firstname,
            @QueryParam("lastname") @DefaultValue("") String lastname,
            @QueryParam("vat") @DefaultValue("") String vat,
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("10") Integer size
    ) throws EntityInvalidArgumentException {

        // Validate pagination parameters
        if (page < 0) throw new EntityInvalidArgumentException("Page", "InvalidPage");
        if (size <= 0)  throw new EntityInvalidArgumentException("Size", "InvalidSize");

        TeacherFiltersDTO filtersDTO = new TeacherFiltersDTO(firstname, lastname, vat);
        Map<String, Object> criteria = Mapper.mapToCriteria(filtersDTO);

        List<TeacherReadOnlyDTO> items = teacherService.getTeachersByCriteriaPaginated(criteria, page, size);

        // Get total count for the same criteria
        long totalItems = teacherService.getTeachersCountByCriteria(criteria);

        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalItems / size);

//        compiler infers the generic type T from the type of items
        return new PaginatedResult<>(
                items,
                page,
                size,
                totalPages,
                totalItems
        );
    }
}