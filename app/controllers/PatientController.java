package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dto.PatientDTO;
import io.swagger.annotations.*;
import lombok.Data;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.HomeService;
import services.PatientService;

import javax.inject.Inject;


/**
 * This controller contains an action to handle HTTP requests to the application's home page.
 */
@Data
@Api(value = "Patients", description = "Operations with Patients", produces = "application/json")
public class PatientController extends Controller {

    private final PatientService patientService;


    @Inject
    public PatientController(HomeService service, PatientService patientService) {
        this.patientService = patientService;
    }


    /**
     * Get List of Patients.
     * @return Patients
     */
    @ApiOperation(value = "Get List of Patients", notes = "Get Patients all available", response = PatientDTO[].class)
    public Result getPatients() {
        return ok(Json.toJson(patientService.getPatients()));
    }

    /**
     * Get a Patient by id.
     * @param id Patient id
     * @return a Patient
     */
    @ApiOperation(value = "Get a Patient by id", response = PatientDTO.class)
    public Result getPatientById(@ApiParam(value = "id", type = "string", required = true) Long id) {
        return patientService.findById(id).map(resource ->
                ok(Json.toJson(resource))
        ).orElseGet(() ->
                notFound()
        );
    }

    /**
     * Add new Patient.
     * @return created Patient.
     */
    @ApiOperation(value = "Add new Patient", response = PatientDTO.class, httpMethod = "POST", consumes = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "body",
                            dataType = "dto.PatientDTO",
                            required = true,
                            paramType = "body",
                            value = "Patient"
                    )
            }
    )
    @BodyParser.Of(BodyParser.Json.class)
   // @ValidateRequest(PatientDTO.class)
    public Result addPatient()  {
        JsonNode json = request().body().asJson();
        final PatientDTO resource = Json.fromJson(json, PatientDTO.class);
        return created(Json.toJson(patientService.createPatient(resource)));
    }

    /**
     * Update Patient by id.
     *
     * @param id of Patient
     * @return updated Patient
     */
    @ApiOperation(value = "Update Patient by id", response = PatientDTO.class, httpMethod = "PUT", consumes = "application/json")
    @ApiImplicitParams(
            {@ApiImplicitParam(
                    name = "body",
                    dataType = "dto.PatientDTO",
                    required = true,
                    paramType = "body",
                    value = "Course"
            )}
    )
    public Result updatePatientById(@ApiParam(value = "patient id", required = true) Long id) {
        JsonNode json = request().body().asJson();
        PatientDTO resource = Json.fromJson(json, PatientDTO.class);
        return patientService.updatePatientById(resource).map(optionalResource -> ok(Json.toJson(optionalResource)))
                .orElseGet(() ->
                        notFound()
                );
    }

    /**
     * Delete Patient by id.
     * @param id Patient
     * @return Boolean
     */
    @ApiOperation(value = "Delete Patient ", response = Boolean.class, httpMethod = "DELETE")
    public Result deletePatientById(@ApiParam(value = "id", type = "string", required = true) Long id) {
        return ok(Json.toJson(patientService.deletePatient(id)));
    }

}
