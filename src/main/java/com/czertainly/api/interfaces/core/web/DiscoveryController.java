package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.discovery.DiscoveryDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.discovery.DiscoveryHistoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping("/v1/discoveries")
@Tag(name = "Discovery Management API", description = "Discovery Management API")
@ApiResponses(
		value = {
				@ApiResponse(
						responseCode = "400",
						description = "Bad Request",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
				@ApiResponse(
						responseCode = "404",
						description = "Not Found",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
				@ApiResponse(
						responseCode = "500",
						description = "Internal Server Error",
						content = @Content
				),
				@ApiResponse(
						responseCode = "502",
						description = "Connector Error",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
				@ApiResponse(
						responseCode = "503",
						description = "Connector Communication Error",
						content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
				),
		})

public interface DiscoveryController {
	
	@Operation(summary = "List Discovery")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of available Discoveries")})
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<DiscoveryHistoryDto> listDiscovery();
	
	@Operation(summary = "Discovery Details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Discovery details retrieved")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public DiscoveryHistoryDto getDiscovery(@Parameter(description = "Discovery UUID") @PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Create Discovery")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Discovery Created", content = @Content(schema = @Schema(implementation = UuidDto.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
					examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}))})
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> createDiscovery(@RequestBody DiscoveryDto request)
			throws AlreadyExistException, NotFoundException, CertificateException, InterruptedException, ConnectorException;
	
	@Operation(summary = "Delete Discovery")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Discovery deleted")})
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeDiscovery(@Parameter(description = "Discovery UUID") @PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Delete Multiple Discoveries")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Discoveries deleted")})
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkRemoveDiscovery(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Discovery UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
			examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
										@RequestBody List<String> discoveryUuids) throws NotFoundException;
}
