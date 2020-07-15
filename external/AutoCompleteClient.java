package external;

import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_autocomplete.*;
import com.smartystreets.api.us_street.Candidate;
import com.smartystreets.api.ClientBuilder;

import java.io.IOException;
import java.util.List;


public class AutoCompleteClient {

	public static List<Candidate> completeAddress(String address) throws IOException, SmartyException {
		String authId = "0013481a-b3e8-13b8-7efe-e4ca14d85bce";
		String authToken = "bD7m8jU06oq5ZQaJ3LNR";
		StaticCredentials credentials = new StaticCredentials(authId, authToken);

		Client autoCompleteClient = new ClientBuilder(credentials).buildUsAutocompleteApiClient();
		Lookup lookup = new Lookup(address);

		// client.send(lookup);

		// System.out.println("*** Result with no filter ***");
		// System.out.println();
		// for (Suggestion suggestion : lookup.getResult()) {
		// System.out.println(suggestion.getText());
		// }

		// Documentation for input fields can be found at:
		// https://smartystreets.com/docs/cloud/us-autocomplete-api#http-request-input-fields

		//lookup.addStateFilter("CA");
		lookup.addCityFilter("San Francisco");
		//lookup.addPrefer("San Francisco,CA");
		lookup.setMaxSuggestions(2);
		lookup.setPreferRatio(0.33333);

		Suggestion[] suggestions = autoCompleteClient.send(lookup); // The client will also return the suggestions directly
		
		List<Candidate> result = SmartyStreetsClient.getValidAddresses(suggestions);
	
//		System.out.println();
//		System.out.println("*** Result with some filters ***");
//		for (Candidate candidate : result) {
//			System.out.println(candidate.getDeliveryLine1() + ", " + candidate.getComponents().getCityName() + ", "
//					+ candidate.getComponents().getState() + " " + candidate.getComponents().getZipCode() + "-" + candidate.getComponents().getPlus4Code());
//		}
		return result;
	}
}
