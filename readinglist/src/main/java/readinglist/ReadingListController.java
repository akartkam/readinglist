package readinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ReadingListController {
	private ReadingListRepository readingListRepository;
	private ReaderRepository readerRepository;

	@Autowired
	public ReadingListController(ReadingListRepository readingListRepository,
			ReaderRepository readerRepository) {
		this.readingListRepository = readingListRepository;
		this.readerRepository = readerRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String readersBooks(Reader reader, Model model) {
		List<Book> readingList = readingListRepository.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
			model.addAttribute("reader", reader);
		}
		return "readingList";
	}


	@RequestMapping(value = "/{reader}", method = RequestMethod.GET)
	public String readersBooks1(@PathVariable("reader") String reader, Model model) {
		Reader r = readerRepository.findByUsername(reader);
		List<Book> readingList = readingListRepository.findByReader(r);
		if (readingList != null) {
			model.addAttribute("books", readingList);
			model.addAttribute("reader", reader);
		}
		return "readingList";
	}

	@RequestMapping(value = "/{reader}", method = RequestMethod.POST)
	public String addToReadingList(@PathVariable("reader") String reader,
			Book book) {
		Reader r = readerRepository.findByUsername(reader);
		book.setReader(r);
		readingListRepository.save(book);
		return "redirect:/{reader}";
	}
}