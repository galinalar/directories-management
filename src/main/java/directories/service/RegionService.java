package directories.service;

import directories.exception.ResponseException;
import directories.mapper.RegionMapper;
import directories.model.Region;
import directories.model.Update;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for regions directories
 */

@Service
@Component
public class RegionService {

    private static final Logger LOGGER = LogManager.getLogger(RegionService.class);

    private static final String EMPTY_RESPONSE = "Directory is empty";
    private static final String NO_REGION_RESPONSE = "No such region";
    private static final String ALREADY_EXISTS = "Region with such data already exists";
    private static final String ALREADY_DELETE = "Region has already been deleted or does not exist";

    private static final String SUCCESSFULLY_DELETE = "Region successfully deleted";

    @Autowired
    RegionMapper regionMapper;

    /**
     * Get region's data from database by index.
     * @param id index of region
     * @return region
     */
    @Cacheable("findById")
    public Region findRegion(Long id) {
        LOGGER.info("get region with index " + id);
        Region region = regionMapper.findById(id);
        if (region != null){
            return region;
        }
        else throw new ResponseException(NO_REGION_RESPONSE);
    }

    /**
     * Get all regions from database
     * @return list of region
     */
   // @Cacheable("findAllRegions")
    public List<Region> findAllRegions() {
        LOGGER.info("get all regions");
        List<Region> regions = regionMapper.findAllRegions();
        if (regions.size()!=0){
            return regions;
        }
        else throw new ResponseException(EMPTY_RESPONSE);
    }

    /**
     * Get regions data from database by name.
     * @param name full name of region
     * @return list of region
     */
    //@Cacheable("findByName")
    public List<Region> findByName(String name) {
        LOGGER.info("get regions with name " + name);
        List<Region> regions = regionMapper.findByName(name);
        if (regions.size()!=0){
            return regions;
        }
        else throw new ResponseException(NO_REGION_RESPONSE);
    }

    /**
     * Get regions data from database by shortname.
     * @param shortname short name of region
     * @return list of region
     */
    //@Cacheable("findByShortName")
    public List<Region> findByShortName(String shortname) {
        LOGGER.info("get regions with shortname " + shortname);
        List<Region> regions = regionMapper.findByShortName(shortname);
        if (regions.size()!=0){
            return regions;
        }
        else throw new ResponseException(NO_REGION_RESPONSE);
    }

    /**
     * Get region data from database by name and shortname.
     * @param name full name of region
     * @param shortname short name of region
     * @return region data
     */
    //@Cacheable("findByNameAndShortName")
    public Region findByNameAndShortName(String name, String shortname) {
        LOGGER.info("get regions with name " + name + " and shortname "+ shortname);
        Region region = regionMapper.findByNameAndShortName(name, shortname);
        if (region != null){
            return region;
        }
        else throw new ResponseException(NO_REGION_RESPONSE);
    }

    /**
     * Insert region into database
     * @param region data of region
     * @return added region
     */
    public Region add(Region region) {
        LOGGER.info("insert region by data: name " + region.getName() + " shortname " + region.getShortName());
        if (regionMapper.findByNameAndShortName(region.getName(), region.getShortName()) == null){
            regionMapper.addRegion(region.getName(), region.getShortName());
            return regionMapper.findByNameAndShortName(region.getName(), region.getShortName());
        }
        else throw new ResponseException(ALREADY_EXISTS);
    }

    /**
     * Update region into database by index
     * @param region data of region
     * @return updated region
     */
    public Region updateRegion(Region region) {
        LOGGER.info("update region by index " + region.getId());
        if (regionMapper.findById(region.getId()) != null){
            if (regionMapper.findByNameAndShortName(region.getName(), region.getShortName())==null){
                regionMapper.updateRegion(region.getId(), region.getName(), region.getShortName());
                return regionMapper.findById(region.getId());
            }
            else throw new ResponseException(ALREADY_EXISTS);
        }
        else throw new ResponseException(NO_REGION_RESPONSE);
    }

    /**
     * Update region's name into database by index
     * @param region data of region
     * @return updated region
     */
    public Region updateNameRegion(Region region) {
        LOGGER.info("update name of region by " + region.getId());
        if (regionMapper.findById(region.getId()) != null){
            if (regionMapper.findByNameAndShortName(region.getName(),
                    regionMapper.findById(region.getId()).getShortName())==null){
                regionMapper.updateNameRegion(region.getId(), region.getName());
                return regionMapper.findById(region.getId());
            }
            else throw new ResponseException(ALREADY_EXISTS);
        }
        else throw new ResponseException(NO_REGION_RESPONSE);
    }

    /**
     * Update region's shortname into database by index
     * @param region data of region
     * @return updated region
     */
    public Region updateShortNameRegion(Region region) {
        LOGGER.info("update shortname of region by " + region.getId());
        if (regionMapper.findById(region.getId()) != null){
            if (regionMapper.findByNameAndShortName(regionMapper.findById(region.getId()).getName(),
                    region.getShortName())==null){
                regionMapper.updateShortNameRegion(region.getId(), region.getShortName());
                return regionMapper.findById(region.getId());
            }
            else throw new ResponseException(ALREADY_EXISTS);
        }
        else throw new ResponseException(NO_REGION_RESPONSE);
    }

    /**
     * Update region's name into database by shortname
     * @param update new and last names
     * @return updated regions
     */
    public List<Region> updateNameRegionByName(Update update) {
        LOGGER.info("update name with name" + update.getLastName() + " to " + update.getNewName());
        if (regionMapper.findByName(update.getLastName()).size()!=0){
            regionMapper.updateNameRegionByName(update.getLastName(), update.getNewName());
            return regionMapper.findByName(update.getNewName());
        }
        else throw new ResponseException(NO_REGION_RESPONSE);
    }

    /**
     * Update region's shortname into database by shortname
     * @param update new and last shortnames
     * @return updated regions
     */
    public List<Region> updateShortNameRegionByShortName(Update update) {
        LOGGER.info("update shortname with shortname" + update.getLastName() + " to " + update.getNewName());
        if (regionMapper.findByShortName(update.getLastName()).size()!=0){
            regionMapper.updateShortNameRegionByShortName(update.getLastName(), update.getNewName());
            return regionMapper.findByShortName(update.getNewName());
        }
        else throw new ResponseException(NO_REGION_RESPONSE);
    }

    /**
     * Delete region from database by index
     * @param id index of region
     * @return deletion success message
     */
    public String deleteRegionById(Long id) {
        LOGGER.info("delete region by index " + id);
        if (regionMapper.findById(id) != null){
            regionMapper.deleteRegionById(id);
            return SUCCESSFULLY_DELETE;
        }
        else throw new ResponseException(ALREADY_DELETE);
    }

    /**
     * Delete region from database by name
     * @param name full name of region
     * @return deletion success message
     */
    public String deleteRegionByName(String name) {
        LOGGER.info("delete regions with name " + name);
        if (regionMapper.findByName(name).size()!=0){
            regionMapper.deleteRegionByName(name);
            return SUCCESSFULLY_DELETE;
        }
        else throw new ResponseException(ALREADY_DELETE);
    }

    /**
     * Delete region from database by shortname.
     * @param shortname short name of region
     * @return deletion success message
     */
    public String deleteRegionByShortName(String shortname) {
        LOGGER.info("delete regions with shortname " + shortname);
        if (regionMapper.findByShortName(shortname).size()!=0){
            regionMapper.deleteRegionByShortName(shortname);
            return SUCCESSFULLY_DELETE;
        }
        else throw new ResponseException(ALREADY_DELETE);
    }

    /**
     * Delete region data from database by name and shortname.
     * @param name full name of region
     * @param shortname short name of region
     * @return deletion success message
     */
    public String deleteRegionByNameAndShortName(String name, String shortname) {
        LOGGER.info("delete regions with name " + name + " and shortname "+ shortname);
        if (regionMapper.findByNameAndShortName(name, shortname) != null){
            regionMapper.deleteRegionByNameAndShortName(name, shortname);
            return SUCCESSFULLY_DELETE;
        }
        else throw new ResponseException(ALREADY_DELETE);
    }

}
