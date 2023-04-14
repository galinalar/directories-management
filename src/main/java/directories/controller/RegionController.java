package directories.controller;

import directories.exception.ResponseException;
import directories.model.Region;
import directories.model.Update;
import directories.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Region REST controller
 */

@RestController
@RequestMapping("/region")
@Api(value = "RegionController")
public class RegionController {

    @Autowired
    RegionService regionService;

    private static final String EMPTY_ID = "ID can't be zero";

    @GetMapping(path = "/get", produces = "application/json")
    @ApiOperation("Get region's data by id")
    public Region getRegion(@RequestBody Long id) {
        if (id!=null){
            return regionService.findRegion(id);
        }
        else throw new ResponseException(EMPTY_ID);
    }

    @GetMapping(path = "/get_all", produces = "application/json")
    @ApiOperation("Get all region's data")
    public List<Region> getRegions() {
        return regionService.findAllRegions();
    }

    @GetMapping(path = "/get_by_name", produces = "application/json")
    @ApiOperation("Get region's data by name")
    public List<Region> getRegionByName(@RequestBody String name){
        return regionService.findByName(name);
    }

    @GetMapping(path = "/get_by_shortname", produces = "application/json")
    @ApiOperation("Get region's data by shortname")
    public List<Region> getRegionByShortname(@RequestBody String shortname){
        return regionService.findByShortName(shortname);
    }

    @GetMapping(path = "/get_by_name_and_shortname", produces = "application/json")
    @ApiOperation("Get region's data by shortname and name")
    public Region getRegionByNameAndShortName(@RequestBody Region region){
        return regionService.findByNameAndShortName(region.getName(), region.getShortName());
    }

    @PostMapping(path = "/add", produces = "application/json")
    @ApiOperation("Add region in database")
    public Region addRegion(@RequestBody Region region) {
        return regionService.add(region);
    }

    @PutMapping(path = "/update", produces = "application/json")
    @ApiOperation("Update region's data in database")
    public Region updateRegion(@RequestBody Region region){
        return regionService.updateRegion(region);
    }

    @PutMapping(path = "/update_name", produces = "application/json")
    @ApiOperation("Update region's name in database")
    public Region updateNameRegion(@RequestBody Region region){
        return regionService.updateNameRegion(region);
    }

    @PutMapping(path = "/update_shortname", produces = "application/json")
    @ApiOperation("Update region's shortName in database")
    public Region updateShortNameRegion(@RequestBody Region region){
        return regionService.updateShortNameRegion(region);
    }

    @PutMapping(path = "/update_name_by_name", produces = "application/json")
    @ApiOperation("Update region's name in database by name")
    public List<Region> updateNameRegionByName(@RequestBody Update update){
        return regionService.updateNameRegionByName(update);
    }

    @PutMapping(path = "/update_shortname_by_shortname", produces = "application/json")
    @ApiOperation("Update region's shortName in database by shortName")
    public List<Region> updateShortNameRegionByShortName(@RequestBody Update update){
        return regionService.updateShortNameRegionByShortName(update);
    }

    @DeleteMapping(path = "/delete", produces = "application/json")
    @ApiOperation("Delete region from database by id")
    public String deleteRegion(@RequestBody Long id){
        return regionService.deleteRegionById(id);
    }

    @DeleteMapping(path = "/delete_by_name", produces = "application/json")
    @ApiOperation("Delete region from database by name")
    public String deleteRegionByName(@RequestBody String name){
        return regionService.deleteRegionByName(name);
    }

    @DeleteMapping(path = "/delete_by_shortname", produces = "application/json")
    @ApiOperation("Delete region from database by shortName")
    public String deleteRegionByShortName(@RequestBody String shortName){
        return regionService.deleteRegionByShortName(shortName);
    }

    @DeleteMapping(path = "/delete_by_name_and_shortname", produces = "application/json")
    @ApiOperation("Delete region from database by name and shortName")
    public String deleteRegionByShortName(@RequestBody Region region){
        return regionService.deleteRegionByNameAndShortName(region.getName(), region.getShortName());
    }
}
