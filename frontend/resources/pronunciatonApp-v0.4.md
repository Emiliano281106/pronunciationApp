# PronunciationApp Frontend v0.4

## Introduction

Links:

- [accordion.md at frontend-react](https://github.com/AlbertProfe/pronunciationApp/blob/frontend-react/frontend/resources/react-dev/accordion.md)

`React.dev`

- [Sharing State Between Components](https://react.dev/learn/sharing-state-between-components)
- [Controlled and uncontrolled components](https://react.dev/learn/sharing-state-between-components#controlled-and-uncontrolled-components)
- [Recap](https://react.dev/learn/sharing-state-between-components#recap "Link for Recap")

`CodeSanbox`

- [CodeSandbox code](https://codesandbox.io/p/sandbox/nt8zkz?file=%2Fsrc%2FFAQ.jsx)
- [CodeSandbox render deploy](https://nt8zkz.csb.app/)

## Adding FAQs

> In this version we are going to add a new FAQ page to our `PronunciationApp`, considering that we're using `Material-UI`, `React Router`, and `Axios`.

Here's a step-by-step process:

1. **Create a new FAQ component**

First, we will create a new file called `FAQ.js` in your components directory. This will contain the FAQ component we discussed earlier:

```jsx
import React, { useState, useEffect } from 'react';
import { Typography, Accordion, AccordionSummary, AccordionDetails } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

const faqData = [
  // Your FAQ data here or we could import from a json or via get axios
];

function FAQ() {
  const [expanded, setExpanded] = useState(false);

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Frequently Asked Questions
      </Typography>
      {faqData.map((faq, index) => (
        <Accordion
          key={faq.index}
          expanded={expanded === `panel${index}`}
          onChange={handleChange(`panel${index}`)}
        >
          <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls={`panel${index}bh-content`}
            id={`panel${index}bh-header`}
          >
            <Typography>{faq.question}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <Typography>{faq.answer}</Typography>
          </AccordionDetails>
        </Accordion>
      ))}
    </div>
  );
}

export default FAQ;
```

2. **Add a new route**

In our main routing file (often `App.js` or a separate `Routes.js`), add a new route for the FAQ page:

```jsx
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import FAQ from './components/FAQ';

function App() {
  return (
    <Router>
      <Switch>
        {/* Your existing routes */}
        <Route path="/faq" component={FAQ} />
      </Switch>
    </Router>
  );
}
```

3. **Add a navigation link**

In our navigation component or wherever we have our menu, add a link to the FAQ page:

```jsx
import { Link } from 'react-router-dom';
import { Button } from '@mui/material';

function Navigation() {
  return (
    <nav>
      {/* Your other navigation items */}
      <Button component={Link} to="/faq" color="inherit">
        FAQ
      </Button>
    </nav>
  );
}
```

4. **Styling (optional)**

If we need additional styling, we can create a separate CSS file for our FAQ component or use Material-UI's `makeStyles` hook:

```jsx
import { makeStyles } from '@mui/styles';

const useStyles = makeStyles((theme) => ({
  faqContainer: {
    maxWidth: 800,
    margin: '0 auto',
    padding: theme.spacing(3),
  },
  // Add more custom styles as needed
}));

function FAQ() {
  const classes = useStyles();

  return (
    <div className={classes.faqContainer}>
      {/* Your FAQ content */}
    </div>
  );
}
```

5. **Fetching FAQ data (optional)**

If we want to fetch FAQ data from an API instead of hardcoding it, we can use `Axios`:

```jsx
import axios from 'axios';

function FAQ() {
  const [faqData, setFaqData] = useState([]);

  useEffect(() => {
    const fetchFAQs = async () => {
      try {
        const response = await axios.get('/api/faqs');
        setFaqData(response.data);
      } catch (error) {
        console.error('Error fetching FAQs:', error);
      }
    };

    fetchFAQs();
  }, []);

  // Rest of your component
}
```

6. **Implement localStorage for expanded panels**

Modify our  FAQ component to use `localStorage`:

```jsx
import React, { useState, useEffect } from 'react';
import { Typography, Accordion, AccordionSummary, AccordionDetails } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

const faqData = [
  // Your FAQ data here
];

function FAQ() {
  const [expandedPanels, setExpandedPanels] = useState(() => {
    const saved = localStorage.getItem('faqExpandedPanels');
    return saved ? new Set(JSON.parse(saved)) : new Set();
  });

  useEffect(() => {
    localStorage.setItem('faqExpandedPanels', JSON.stringify(Array.from(expandedPanels)));
  }, [expandedPanels]);

  const handleChange = (panel) => (event, isExpanded) => {
    setExpandedPanels(prev => {
      const newSet = new Set(prev);
      if (isExpanded) {
        newSet.add(panel);
      } else {
        newSet.delete(panel);
      }
      return newSet;
    });
  };

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Frequently Asked Questions
      </Typography>
      {faqData.map((faq, index) => {
        const panelName = `panel${index}`;
        return (
          <Accordion
            key={faq.index}
            expanded={expandedPanels.has(panelName)}
            onChange={handleChange(panelName)}
          >
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls={`${panelName}-content`}
              id={`${panelName}-header`}
            >
              <Typography>{faq.question}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Typography>{faq.answer}</Typography>
            </AccordionDetails>
          </Accordion>
        );
      })}
    </div>
  );
}

export default FAQ;
```

This implementation does the following:

1. **Initial State**:
   
   - We initialize `expandedPanels` state by checking localStorage for any previously saved state.
   
   - If found, we parse it and create a Set; otherwise, we start with an empty Set.

2. **Persisting State**:
   
   - We use a `useEffect` hook to save the current state of `expandedPanels` to `localStorage` whenever it changes.
   
   - The Set is converted to an array and then to a JSON string for storage.

3. **Handling Expansion**:
   
   - The `handleChange` function now updates the `expandedPanels` <mark>Set</mark>, adding or removing panel names as they are expanded or collapsed.

4. **Rendering**:
   
   - Each Accordion's `expanded` prop is now determined by checking if its panel name is in the `expandedPanels` Set.

This approach ensures that:

- The `state` of expanded FAQ items <mark>persists across page reloads</mark>.
- Users can pick up where they left off when they return to the FAQ page.
- The implementation is efficient, using a <mark>Set</mark> for quick lookups and updates.

Remember to test this implementation thoroughly, especially in different browsers and private browsing modes, as localStorage behavior can vary slightly in these contexts.
