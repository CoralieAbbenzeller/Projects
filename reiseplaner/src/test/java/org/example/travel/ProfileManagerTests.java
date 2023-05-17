package org.example.travel;

import junit.framework.TestCase;
import org.example.utilites.invalidAgeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.example.travel.MyTravel.profileManagerInstance;
import static org.junit.Assert.assertEquals;

public class ProfileManagerTests {

    @Test(expected = invalidAgeException.class)
    public void testInvalidAgeException() throws FileNotFoundException {
        var p = new ProfileManager();
        p.validateAge(-3);
    }

    @Test
    public void beforeLoginGetLoggedInProfileIsNullNull() throws IOException {

        var p = new ProfileManager();
        var pl = new Profile(null, null);

        assertEquals(pl.getUsername(), p.getLoggedInProfile().getUsername());
        assertEquals(pl.getPassword(), p.getLoggedInProfile().getPassword());
    }

}