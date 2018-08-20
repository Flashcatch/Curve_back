package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dto.AppointmentDTO;
import io.swagger.annotations.*;
import lombok.Data;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.AppointmentService;

import javax.inject.Inject;


@Data
@Api(value = "Appointments", description = "Operations with Appointments", produces = "application/json")
public class AppointmentController extends Controller {

    private final AppointmentService AppointmentService;


    @Inject
    public AppointmentController(AppointmentService AppointmentService) {
        this.AppointmentService = AppointmentService;
    }


    /**
     * Get List of Appointments.
     * @return Appointments
     */
    @ApiOperation(value = "Get List of Appointments", notes = "Get Appointments all available", response = AppointmentDTO[].class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "startDate",
                    dataType = "string",
                    paramType = "query",
                    value = "Filter by date"
            )
    })
    public Result getAppointments() {
        String startDate = request().getQueryString("startDate");

        return ok(Json.toJson(AppointmentService.getAppointments(startDate)));
    }

    /**
     * Get a Appointment by id.
     * @param id Appointment id
     * @return a Appointment
     */
    @ApiOperation(value = "Get a Appointment by id", response = AppointmentDTO.class)
    public Result getAppointmentById(@ApiParam(value = "id", type = "string", required = true) Long id) {
        return AppointmentService.findById(id).map(resource ->
                ok(Json.toJson(resource))
        ).orElseGet(() ->
                notFound()
        );
    }

    /**
     * Add new Appointment.
     * @return created Appointment.
     */
    @ApiOperation(value = "Add new Appointment", response = AppointmentDTO.class, httpMethod = "POST", consumes = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "body",
                            dataType = "dto.AppointmentDTO",
                            required = true,
                            paramType = "body",
                            value = "Appointment"
                    )
            }
    )
    @BodyParser.Of(BodyParser.Json.class)
    public Result addAppointment()  {
        JsonNode json = request().body().asJson();
        final AppointmentDTO resource = Json.fromJson(json, AppointmentDTO.class);
        try {
            return created(Json.toJson(AppointmentService.createAppointment(resource)));
        } catch (RuntimeException ex) {
            return badRequest(ex.getMessage());
        }

    }

    /**
     * Update Appointment by id.
     *
     * @param id of Appointment
     * @return updated Appointment
     */
    @ApiOperation(value = "Update Appointment by id", response = AppointmentDTO.class, httpMethod = "PUT", consumes = "application/json")
    @ApiImplicitParams(
            {@ApiImplicitParam(
                    name = "body",
                    dataType = "dto.AppointmentDTO",
                    required = true,
                    paramType = "body",
                    value = "Course"
            )}
    )
    public Result updateAppointmentById(@ApiParam(value = "Appointment id", required = true) Long id) {
        JsonNode json = request().body().asJson();
        AppointmentDTO resource = Json.fromJson(json, AppointmentDTO.class);
        return AppointmentService.updateAppointmentById(resource).map(optionalResource -> ok(Json.toJson(optionalResource)))
                .orElseGet(() ->
                        notFound()
                );
    }

    /**
     * Delete Appointment by id.
     * @param id Appointment
     * @return Boolean
     */
    @ApiOperation(value = "Delete Appointment ", response = Boolean.class, httpMethod = "DELETE")
    public Result deleteAppointmentById(@ApiParam(value = "id", type = "string", required = true) Long id) {
        return ok(Json.toJson(AppointmentService.deleteAppointment(id)));
    }

}
