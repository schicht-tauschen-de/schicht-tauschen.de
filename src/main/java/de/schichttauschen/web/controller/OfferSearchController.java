package de.schichttauschen.web.controller;

import de.schichttauschen.web.data.vo.rest.RestOffer;
import de.schichttauschen.web.data.vo.rest.RestOfferSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/offer")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OfferSearchController {

    @GetMapping("search")
    public Page<RestOffer> search(@RequestBody RestOfferSearchRequest searchRequest) {
        return null;
    }
}