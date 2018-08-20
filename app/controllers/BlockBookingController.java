package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dto.BlockBookingDTO;
import io.swagger.annotations.*;
import lombok.Data;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.BlockBookingService;
import services.HomeService;

import javax.inject.Inject;


@Data
@Api(value = "BlockBookings", description = "Operations with BlockBookings", produces = "application/json")
public class BlockBookingController extends Controller {

    private final BlockBookingService blockBookingService;


    @Inject
    public BlockBookingController(HomeService service, BlockBookingService blockBookingService) {
        this.blockBookingService = blockBookingService;
    }


    /**
     * Get List of BlockBookings.
     * @return BlockBookings
     */
    @ApiOperation(value = "Get List of BlockBookings", notes = "Get BlockBookings all available", response = BlockBookingDTO[].class)
    public Result getBlockBookings() {
        return ok(Json.toJson(blockBookingService.getBlockBookings()));
    }

    /**
     * Get a BlockBooking by id.
     * @param id BlockBooking id
     * @return a BlockBooking
     */
    @ApiOperation(value = "Get a BlockBooking by id", response = BlockBookingDTO.class)
    public Result getBlockBookingById(@ApiParam(value = "id", type = "string", required = true) Long id) {
        return blockBookingService.findById(id).map(resource ->
                ok(Json.toJson(resource))
        ).orElseGet(() ->
                notFound()
        );
    }

    /**
     * Add new BlockBooking.
     * @return created BlockBooking.
     */
    @ApiOperation(value = "Add new BlockBooking", response = BlockBookingDTO.class, httpMethod = "POST", consumes = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "body",
                            dataType = "dto.BlockBookingDTO",
                            required = true,
                            paramType = "body",
                            value = "BlockBooking"
                    )
            }
    )
    @BodyParser.Of(BodyParser.Json.class)
    public Result addBlockBooking()  {
        JsonNode json = request().body().asJson();
        final BlockBookingDTO resource = Json.fromJson(json, BlockBookingDTO.class);
        try {
        return created(Json.toJson(blockBookingService.createBlockBooking(resource)));
        } catch (RuntimeException ex) {

            return badRequest(ex.getMessage());
        }
    }

    /**
     * Update BlockBooking by id.
     *
     * @param id of BlockBooking
     * @return updated BlockBooking
     */
    @ApiOperation(value = "Update BlockBooking by id", response = BlockBookingDTO.class, httpMethod = "PUT", consumes = "application/json")
    @ApiImplicitParams(
            {@ApiImplicitParam(
                    name = "body",
                    dataType = "dto.BlockBookingDTO",
                    required = true,
                    paramType = "body",
                    value = "Course"
            )}
    )
    public Result updateBlockBookingById(@ApiParam(value = "BlockBooking id", required = true) Long id) {
        JsonNode json = request().body().asJson();
        BlockBookingDTO resource = Json.fromJson(json, BlockBookingDTO.class);
        return blockBookingService.updateBlockBookingById(resource).map(optionalResource -> ok(Json.toJson(optionalResource)))
                .orElseGet(() ->
                        notFound()
                );
    }

    /**
     * Delete BlockBooking by id.
     * @param id BlockBooking
     * @return Boolean
     */
    @ApiOperation(value = "Delete BlockBooking ", response = Boolean.class, httpMethod = "DELETE")
    public Result deleteBlockBookingById(@ApiParam(value = "id", type = "string", required = true) Long id) {
        return ok(Json.toJson(blockBookingService.deleteBlockBooking(id)));
    }

}
