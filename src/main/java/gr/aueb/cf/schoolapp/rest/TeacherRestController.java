package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.security.ITeacherService;
import gr.aueb.cf.schoolapp.validator.ValidatorUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))     // ME LOMBOK
@Path("/teachers")// ME LOMBOK
public class TeacherRestController {

    private final ITeacherService teacherService;

    // ΧΩΡΙΣ LOMBOK
//    @Inject
//    public TeacherRestController(ITeacherService teacherService) {
//        this.teacherService = teacherService;
//    }

    @POST
    @Path("")   // ΘΑ ΜΠΟΡΟΥΣΑΜΕ ΝΑ ΤΟ ΠΑΡΑΛΕΙΨΟΥΜΕ ΤΟ PATH ΣΤΗΝ ΠΕΡΙΠΤΩΣΗ ΕΔΩ
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeacher(TeacherInsertDTO insertDTO, @Context UriInfo uriInfo) throws EntityInvalidArgumentException, EntityAlreadyExistsException {
        List<String> errors = ValidatorUtil.validateDTO(insertDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentException("Teacher", String.join("\n", errors));
        }

        TeacherReadOnlyDTO readOnlyDTO = teacherService.insertTeacher(insertDTO);
        URI newResourceUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(readOnlyDTO.id()))
                .build();

        return Response
                .created(newResourceUri)
                .entity(readOnlyDTO)
                .build();
    }

    @PUT
    @Path("/{teacherId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeacher(@PathParam("teacherId") Long teacherId, TeacherUpdateDTO updateDTO) throws EntityInvalidArgumentException, EntityNotFoundException {
        List<String> errors = ValidatorUtil.validateDTO(updateDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentException("Teacher", String.join("\n", errors));
        }

        TeacherReadOnlyDTO readOnlyDTO = teacherService.updateTeacher(updateDTO);

        return Response
                .status(Response.Status.OK)
                .entity(readOnlyDTO)
                .build();

    }
}
