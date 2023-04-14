package directories.service;

import directories.exception.ResponseException;
import directories.model.Region;
import directories.model.Update;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * This @code{ARegionServiceIntegrationTest} class tests @code{RegionService} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionServiceIntegrationTest {

    private static final String EMPTY_RESPONSE = "Directory is empty";
    private static final String NO_REGION_RESPONSE = "No such region";
    private static final String ALREADY_EXISTS = "Region with such data already exists";
    private static final String ALREADY_DELETE = "Region has already been deleted or does not exist";

    private static final String SUCCESSFULLY_DELETE = "Region successfully deleted";

    @Autowired
    private RegionService regionService;

    @Test
    public void TestAll() {

        Region region1 = new Region();
        region1.setName("Region1");
        region1.setShortName("R1");
        Region regionTest = regionService.add(region1);

        Assert.assertNotNull(regionTest);
        Assert.assertEquals(java.util.Optional.of(1L), java.util.Optional.of(regionTest.getId()));
        Assert.assertEquals("R1", regionTest.getShortName());
        Assert.assertEquals("Region1", regionTest.getName());

        Region region2 = new Region();
        region2.setName("Region2");
        region2.setShortName("R1");
        regionService.add(region2);

        Region region3 = new Region();
        region3.setName("Region2");
        region3.setShortName("R3");
        regionService.add(region3);

        Region region = regionService.findRegion(3L);
        Assert.assertNotNull(region);
        Assert.assertEquals(java.util.Optional.of(3L), java.util.Optional.of(region.getId()));
        Assert.assertEquals("R3", region.getShortName());
        Assert.assertEquals("Region2", region.getName());

        Assert.assertThrows(NO_REGION_RESPONSE, ResponseException.class, ()-> regionService.findRegion(4L));

        Region regionByNameAndShortName = regionService.findByNameAndShortName("Region2", "R1");
        Assert.assertNotNull(regionByNameAndShortName);
        Assert.assertEquals(java.util.Optional.of(2L), java.util.Optional.of(regionByNameAndShortName.getId()));

        Assert.assertThrows(NO_REGION_RESPONSE, ResponseException.class,
                ()-> regionService.findByNameAndShortName("Region33", "R33"));

        List<Region> regions = regionService.findAllRegions();
        List<Region> regionsExp = List.of(region1, region2, region3);
        Assert.assertEquals(3, regions.size());
        Assert.assertEquals(regionsExp.get(0).getName(), regions.get(0).getName());
        Assert.assertEquals(regionsExp.get(1).getName(), regions.get(1).getName());
        Assert.assertEquals(regionsExp.get(2).getName(), regions.get(2).getName());

        List<Region> regionsByName = regionService.findByName("Region2");
        List<Region> regionsExpByName = List.of(region2, region3);
        Assert.assertEquals(2, regionsByName.size());
        Assert.assertEquals(regionsExpByName.get(0).getShortName(), regionsByName.get(0).getShortName());
        Assert.assertEquals(regionsExpByName.get(1).getShortName(), regionsByName.get(1).getShortName());

        Assert.assertThrows(NO_REGION_RESPONSE, ResponseException.class, ()-> regionService.findByName("Region33"));

        List<Region> regionsByShortName = regionService.findByShortName("R1");
        List<Region> regionsExpByShortName = List.of(region1, region2);
        Assert.assertEquals(2, regionsByShortName.size());
        Assert.assertEquals(regionsExpByShortName.get(0).getName(), regionsByShortName.get(0).getName());
        Assert.assertEquals(regionsExpByShortName.get(1).getName(), regionsByShortName.get(1).getName());

        Assert.assertThrows(NO_REGION_RESPONSE, ResponseException.class, ()-> regionService.findByShortName("R33"));

        Region region4 = new Region();
        region4.setId(3L);
        region4.setName("Region2");
        region4.setShortName("R2");
        Region regionUpdate = regionService.updateRegion(region4);
        Assert.assertNotNull(regionUpdate);
        Assert.assertEquals(regionUpdate.getId(), region4.getId());
        Assert.assertEquals(regionUpdate.getShortName(), region4.getShortName());
        Assert.assertEquals(regionUpdate.getName(), region4.getName());

        Assert.assertThrows(ALREADY_EXISTS, ResponseException.class, ()-> regionService.updateRegion(region4));

        Region throwsRegion = new Region();
        throwsRegion.setId(6L);
        throwsRegion.setShortName("Region33");
        throwsRegion.setName("Region33");

        Assert.assertThrows(NO_REGION_RESPONSE, ResponseException.class, ()-> regionService.updateRegion(throwsRegion));

        region4.setId(3L);
        region4.setName("Region1");
        Region regionNameUpdate = regionService.updateNameRegion(region4);
        Assert.assertNotNull(regionNameUpdate);
        Assert.assertEquals(regionNameUpdate.getId(), region4.getId());
        Assert.assertEquals(regionNameUpdate.getShortName(), region4.getShortName());
        Assert.assertEquals(regionNameUpdate.getName(), region4.getName());

        Assert.assertThrows(ALREADY_EXISTS, ResponseException.class, ()-> regionService.updateNameRegion(region4));
        Assert.assertThrows(NO_REGION_RESPONSE, ResponseException.class,
                ()-> regionService.updateNameRegion(throwsRegion));

        region4.setId(3L);
        region4.setShortName("R3");
        Region regionShortNameUpdate = regionService.updateShortNameRegion(region4);
        Assert.assertNotNull(regionShortNameUpdate);
        Assert.assertEquals(regionShortNameUpdate.getId(), region4.getId());
        Assert.assertEquals(regionShortNameUpdate.getShortName(), region4.getShortName());
        Assert.assertEquals(regionShortNameUpdate.getName(), region4.getName());

        Assert.assertThrows(ALREADY_EXISTS, ResponseException.class, ()-> regionService.updateShortNameRegion(region4));
        Assert.assertThrows(NO_REGION_RESPONSE, ResponseException.class,
                ()-> regionService.updateShortNameRegion(throwsRegion));

        Update updateName = new Update();
        updateName.setLastName("Region1");
        updateName.setNewName("Region11");
        List<Region> regionsNameByNameUpdate = regionService.updateNameRegionByName(updateName);
        region1.setName(updateName.getNewName());
        region4.setName(updateName.getNewName());
        List<Region> regionsExpNameByShortNameUpdate = List.of(region1, region4);
        Assert.assertEquals(2, regionsNameByNameUpdate.size());
        Assert.assertEquals(regionsExpNameByShortNameUpdate.get(0).getName(), regionsNameByNameUpdate.get(0).getName());
        Assert.assertEquals(regionsExpNameByShortNameUpdate.get(1).getName(), regionsNameByNameUpdate.get(1).getName());
        Assert.assertEquals(regionsExpNameByShortNameUpdate.get(0).getShortName(),
                regionsNameByNameUpdate.get(0).getShortName());
        Assert.assertEquals(regionsExpNameByShortNameUpdate.get(1).getShortName(),
                regionsNameByNameUpdate.get(1).getShortName());

        Update updateThrow = new Update();
        updateName.setLastName("Region33");
        updateName.setNewName("Region33");
        Assert.assertThrows(NO_REGION_RESPONSE, ResponseException.class,
                ()-> regionService.updateNameRegionByName(updateThrow));

        updateName.setLastName("R1");
        updateName.setNewName("R11");
        List<Region> regionsShortNameByShortNameUpdate = regionService.updateShortNameRegionByShortName(updateName);
        region1.setShortName(updateName.getNewName());
        region2.setShortName(updateName.getNewName());
        List<Region> regionsExpShortNameByShortNameUpdate = List.of(region1, region2);
        Assert.assertEquals(2, regionsShortNameByShortNameUpdate.size());
        Assert.assertEquals(regionsExpShortNameByShortNameUpdate.get(0).getName(), regionsShortNameByShortNameUpdate.get(0).getName());
        Assert.assertEquals(regionsExpShortNameByShortNameUpdate.get(1).getName(), regionsShortNameByShortNameUpdate.get(1).getName());
        Assert.assertEquals(regionsExpShortNameByShortNameUpdate.get(0).getShortName(), regionsShortNameByShortNameUpdate.get(0).getShortName());
        Assert.assertEquals(regionsExpShortNameByShortNameUpdate.get(1).getShortName(), regionsShortNameByShortNameUpdate.get(1).getShortName());

        Assert.assertThrows(NO_REGION_RESPONSE, ResponseException.class,
                ()-> regionService.updateShortNameRegionByShortName(updateThrow));

        String regionDeleteById = regionService.deleteRegionById(1L);
        Assert.assertEquals(SUCCESSFULLY_DELETE, regionDeleteById);
        List<Region> regionsAfterDelete = regionService.findAllRegions();
        region2.setId(2L);
        List<Region> regionsExpAfterDelete = List.of(region2, region4);
        Assert.assertEquals(2, regionsAfterDelete.size());
        Assert.assertEquals(regionsExpAfterDelete.get(0).getName(), regionsAfterDelete.get(0).getName());
        Assert.assertEquals(regionsExpAfterDelete.get(1).getName(), regionsAfterDelete.get(1).getName());
        Assert.assertEquals(regionsExpAfterDelete.get(0).getId(), regionsAfterDelete.get(0).getId());
        Assert.assertEquals(regionsExpAfterDelete.get(1).getId(), regionsAfterDelete.get(1).getId());

        Assert.assertThrows(ALREADY_DELETE, ResponseException.class, ()-> regionService.deleteRegionById(33L));

        Region region5 = new Region();
        region5.setName("Region3");
        region5.setShortName("R3");
        regionService.add(region5);

        Region region6 = new Region();
        region6.setName("Region2");
        region6.setShortName("R2");
        regionService.add(region6);

        Region region7 = new Region();
        region7.setName("RegionLast");
        region7.setShortName("RLast");
        regionService.add(region7);

        String regionDeleteByShortName = regionService.deleteRegionByShortName("R3");
        Assert.assertEquals(SUCCESSFULLY_DELETE, regionDeleteByShortName);
        List<Region> regionsAfterDeleteByShortName = regionService.findAllRegions();
        List<Region> regionsExpAfterDeleteByShortName = List.of(region2, region6, region7);
        Assert.assertEquals(3, regionsAfterDeleteByShortName.size());
        Assert.assertEquals(regionsExpAfterDeleteByShortName.get(0).getName(),
                regionsAfterDeleteByShortName.get(0).getName());
        Assert.assertEquals(regionsExpAfterDeleteByShortName.get(1).getName(),
                regionsAfterDeleteByShortName.get(1).getName());
        Assert.assertEquals(regionsExpAfterDeleteByShortName.get(0).getShortName(),
                regionsAfterDeleteByShortName.get(0).getShortName());
        Assert.assertEquals(regionsExpAfterDeleteByShortName.get(1).getShortName(),
                regionsAfterDeleteByShortName.get(1).getShortName());
        Assert.assertEquals(regionsExpAfterDeleteByShortName.get(2).getName(),
                regionsAfterDeleteByShortName.get(2).getName());
        Assert.assertEquals(regionsExpAfterDeleteByShortName.get(2).getShortName(),
                regionsAfterDeleteByShortName.get(2).getShortName());

        Assert.assertThrows(ALREADY_DELETE, ResponseException.class, ()-> regionService.deleteRegionByShortName("R33"));

        String regionDeleteByName = regionService.deleteRegionByName("Region2");
        Assert.assertEquals(SUCCESSFULLY_DELETE, regionDeleteByName);
        List<Region> regionsAfterDeleteByName = regionService.findAllRegions();
        List<Region> regionsExpAfterDeleteByName = List.of(region7);
        Assert.assertEquals(1, regionsAfterDeleteByName.size());
        Assert.assertEquals(regionsExpAfterDeleteByName.get(0).getName(),
                regionsAfterDeleteByName.get(0).getName());
        Assert.assertEquals(regionsExpAfterDeleteByName.get(0).getShortName(),
                regionsAfterDeleteByName.get(0).getShortName());

        Assert.assertThrows(ALREADY_DELETE, ResponseException.class, ()-> regionService.deleteRegionByName("R33"));

        String regionDeleteByNameAndShortName = regionService.deleteRegionByNameAndShortName("RegionLast", "RLast");
        Assert.assertEquals(SUCCESSFULLY_DELETE, regionDeleteByNameAndShortName);

        Assert.assertThrows(ALREADY_DELETE, ResponseException.class,
                ()-> regionService.deleteRegionByNameAndShortName("R33", "R33"));

        Assert.assertThrows(EMPTY_RESPONSE, ResponseException.class, ()-> regionService.findAllRegions());
    }
}
