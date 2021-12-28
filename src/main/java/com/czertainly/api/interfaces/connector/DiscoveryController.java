package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.connector.discovery.DiscoveryDataRequestDto;
import com.czertainly.api.model.connector.discovery.DiscoveryProviderDto;
import com.czertainly.api.model.connector.discovery.DiscoveryRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/discoveryProvider")
@Tag(
        name = "Discovery API",
        description = "Discovery Provider API. " +
                "Used to schedule and establish certificate discovery process. " +
                "Once the discovery process is started, the progress and the history " +
                "of the certificate discovery can be requested. Discovery provides " +
                "information about discovered certificates and meta data that are specific " +
                "to implementation of the discovery provider."
)
public interface DiscoveryController {

    @PostMapping(
            path = "/discover",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @Operation(
            summary = "Initiate certificate Discovery"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Discovery initiated"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorMessageDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable entity"
                    ),
            }
    )
    DiscoveryProviderDto discoverCertificate(@RequestBody DiscoveryRequestDto request) throws IOException, NotFoundException;

    @PostMapping(
            path = "/discover/{uuid}",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @Operation(
            summary = "Get Discovery status and result"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Discovery details retrieved"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorMessageDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    ),
            }
    )
    DiscoveryProviderDto getDiscovery(@PathVariable String uuid, @RequestBody DiscoveryDataRequestDto request) throws IOException, NotFoundException;

}