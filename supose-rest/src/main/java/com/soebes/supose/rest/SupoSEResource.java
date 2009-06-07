package com.soebes.supose.rest;

import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

public class SupoSEResource extends Resource {

	public SupoSEResource(Context context, Request request, Response response) {
		super(context, request, response);
		getVariants().add(new Variant(MediaType.TEXT_PLAIN));
	}

	public Representation represent(Variant variant) throws ResourceException {
		Representation representation = new StringRepresentation(
			"Hello World", MediaType.TEXT_PLAIN
		);
		return representation;
	}
}