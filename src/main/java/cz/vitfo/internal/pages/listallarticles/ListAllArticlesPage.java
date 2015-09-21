package cz.vitfo.internal.pages.listallarticles;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import cz.vitfo.database.daoimpl.ArticleDaoImpl;
import cz.vitfo.database.model.Article;
import cz.vitfo.internal.pages.InternalBasePage;
import cz.vitfo.internal.pages.editwithmodal.EditPage;

public class ListAllArticlesPage extends InternalBasePage {
	
	private static final long serialVersionUID = -5560925637114655639L;

	public ListAllArticlesPage() {
		
		List<IColumn<Article, String>> columns = new ArrayList<>();
		columns.add(new PropertyColumn(Model.of("Category"), "category", "category"));
		columns.add(new PropertyColumn(Model.of("Header"), "header", "header"));
		columns.add(new PropertyColumn(Model.of("Saved"), "saved", "saved"));
		
		ArticlesDataProvider dataProvider = new ArticlesDataProvider();
		DataTable table = new DataTable("articles", columns, dataProvider, 10) {
			@Override
			protected Item newRowItem(String id, int index, final IModel model) {
				Item rowItem = new Item(id, index, model);
				rowItem.add(new AjaxEventBehavior("onclick") {
					@Override
					protected void onEvent(AjaxRequestTarget target) {
						Article article = (Article) model.getObject();
						PageParameters pp = new PageParameters();
						pp.set(0, article.getId());
//						setResponsePage(EditPage.class, pp);		
						setResponsePage(new EditPage(pp));
					}
				});
				return rowItem;
			}
		};
		table.addTopToolbar(new NoRecordsToolbar(table));
		table.addTopToolbar(new HeadersToolbar(table, dataProvider));

		table.addBottomToolbar(new NavigationToolbar(table));
		add(table);
	}

	private static class ArticlesDataProvider extends SortableDataProvider<Article, String> {
		private List<Article> articles;
		
		public ArticlesDataProvider() {
			ArticleDaoImpl dao = new ArticleDaoImpl();
			articles = dao.getAllArticles();
			
			// the defult sorting order
			setSort("saved", SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends Article> iterator(long first, long count) {
			List<Article> data = new ArrayList<>(articles);
			
			// create comparator for article objects
			Comparator comparator = new Comparator<Article>() {

				@Override
				public int compare(Article o1, Article o2) {
					// locale sensitive comparison
					Collator collator = Collator.getInstance(Session.get().getLocale());
					if (getSort() != null) {
						int orderDirection = getSort().isAscending() ? 1 : -1;
					
						if (getSort().getProperty().equals("header")) {
							// compare header names
							return orderDirection * (collator.compare(o1.getHeader(), o2.getHeader()));
						} else if (getSort().getProperty().equals("category")) {
							// compare category names
							if (o1.getCategory() != null && o2.getCategory() != null) {
								return orderDirection * (collator.compare(o1.getCategory().toString(), o2.getCategory().toString()));
							}
						} else if (getSort().getProperty().equals("saved")) {
							// compare timestamp
							int result = (o1.getSaved().getTime() < o2.getSaved().getTime()) ? 1 : -1;
							return orderDirection * result;
						}
					}
					
					return 0;
				}

			};
			// sort data, sorting is specified in comparator's compare() method
			Collections.sort(data, comparator);
			
			// specifies which part of list shoud be returned
			return data.subList((int)first, (int)first + (int)count).iterator();
		}

		@Override
		public long size() {
			return articles.size();
		}

		@Override
		public IModel<Article> model(final Article object) {
			return Model.of(object);
		}
	}
	
	@Override
	protected IModel<String> getSubTitle() {
		return new ResourceModel("submenu.listAllArticlesPage");
	}
}
