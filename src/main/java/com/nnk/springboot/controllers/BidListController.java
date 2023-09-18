package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Controller class responsible for managing BidList-related web requests.
 */
@Controller
public class BidListController {

    private BidListService bidListService;

    /**
     * Constructor for dependency injection.
     *
     * @param bidListService the service class responsible for bid operations
     */
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * Shows the list of all bids.
     *
     * @param model the model object for passing attributes to the view
     * @return the view name
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists", bidListService.getAllBid());
        return "bidList/list";
    }

    /**
     * Displays the form to add a new bid.
     *
     * @param bid the bid object to be added
     * @param model the model object for passing attributes to the view
     * @return the view name
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model) {

        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    /**
     * Validate and save a new bid.
     *
     * @param bid Bid object to be saved
     * @param result BindingResult object containing validation results
     * @param model Model object to pass attributes to the view
     * @return the list view for bids, or the add view if validation fails
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "bidList/add";
        }
        bidListService.saveBidList(bid);
        return "redirect:/bidList/list";
    }

    /**
     * Display the form for updating an existing bid.
     *
     * @param id ID of the bid to be updated
     * @param model Model object to pass attributes to the view
     * @return the update view for bids
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<BidList> bidList = bidListService.getBidById(id);

        model.addAttribute("bidList", bidList.get());
        return "bidList/update";
    }

    /**
     * Update an existing bid.
     *
     * @param id ID of the bid to be updated
     * @param bidList Updated Bid object
     * @param result BindingResult object containing validation results
     * @param model Model object to pass attributes to the view
     * @return the list view for bids, or the update view if validation fails
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "bidList/update";
        }
        bidListService.updateBidList(id ,bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Delete a bid.
     *
     * @param id ID of the bid to be deleted
     * @param model Model object to pass attributes to the view
     * @return the list view for bids
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidById(id);
        return "redirect:/bidList/list";
    }
}
