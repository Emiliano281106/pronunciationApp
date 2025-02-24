# Accordion

Links:

- [Sharing State Between Components](https://react.dev/learn/sharing-state-between-components)
- [Controlled and uncontrolled components](https://react.dev/learn/sharing-state-between-components#controlled-and-uncontrolled-components)
- [Recap](https://react.dev/learn/sharing-state-between-components#recap "Link for Recap")

**Key ideas**

- When you want to coordinate two components, move their state to their common parent.
- Then pass the information down through props from their common parent.
- Finally, pass the event handlers down so that the children can change the parent’s state.
- It’s useful to consider components as “`controlled`” (driven by props) or “`uncontrolled`” (driven by state).

### Children and props

In Simple Terms:

1. **Props (`title`)**: Like labels or settings you pass to customize how a component behaves or looks.

2. **Children (`{children}`)**: The stuff you put *inside* a component to be displayed or used by that component.

By combining these two, React lets you create reusable components that are flexible and dynamic. Let's see it in our example:

```jsx
import { useState } from 'react';

function Panel({ title, children }) {
  const [isActive, setIsActive] = useState(false);
  return (
    <section className="panel">
      <h3>{title}</h3>
      {isActive ? (
        <p>{children}</p>
      ) : (
        <button onClick={() => setIsActive(true)}>
          Show
        </button>
      )}
    </section>
  );
}

export default function Accordion() {
  return (
    <>
      <h2>Almaty, Kazakhstan</h2>
      <Panel title="About">
        With a population of about 2 million, Almaty is Kazakhstan's largest city. From 1929 to 1997, it was its capital city.
      </Panel>
      <Panel title="Etymology">
        The name comes from <span lang="kk-KZ">алма</span>, the Kazakh word for "apple" and is often translated as "full of apples". In fact, the region surrounding Almaty is thought to be the ancestral home of the apple, and the wild <i lang="la">Malus sieversii</i> is considered a likely candidate for the ancestor of the modern domestic apple.
      </Panel>
    </>
  );
}
```

#### What are `props`?

- **Definition**: `props` (short for "properties") are a way to pass data from a parent component to a child component.

- In our example:
  
  - The `Panel` component receives a `title` as a **prop**.
  
  - When you write `<Panel title="About">`, you're passing the string `"About"` as the `title` prop to the `Panel` component.
  
  - Inside the `Panel` component, you can access this data using `{title}`.

Think of `props` like arguments you pass into a function. For example:

```jsx
<Panel title="About" />
```

Here, you're passing `"About"` as a "parameter" to the `Panel` component.

##### What is `children`?

- **Definition**: `children` is a special prop in `React` that represents everything you write <mark>between the opening and closing tags of a component.</mark>

- In our example:
  
  - When you write:
    
    ```jsx
    <Panel title="About">
    With a population of about 2 million, Almaty is Kazakhstan's largest city. From 1929 to 1997, it was its capital city.
    </Panel>
    ```
    
    The text inside `<Panel>` (starting with "With a population...") is passed to the `Panel` component as its **`children`**.
  
  - Inside the `Panel` component, you can use `{children}` to display this content.

Think of `children` like the "content" or "body" of a tag. For example:

```jsx
<Panel title="Etymology">
  This is the children content.
</Panel>
```

Here, `"This is the children content."` is passed as the **children** prop.

How it works together in our example:

1. In our `Accordion` component:
   
   - We use `<Panel>` twice, passing different values for the `title` prop and different content as `children`.
   
   ```jsx
   <Panel title="About">
     With a population of about 2 million...
   </Panel>
   <Panel title="Etymology">
     The name comes from алма...
   </Panel>
   ```

2. Inside the `Panel` component:
   
   - The `{title}` displays the value of the `title` prop (e.g., "About" or "Etymology").
   
   - The `{children}` displays whatever content was written between `<Panel>` and `</Panel>`.
   
   ```jsx
   <section className="panel">
     <h3>{title}</h3> {/* Displays "About" or "Etymology" */}
     {isActive ? (
       <p>{children}</p> {/* Displays the content inside <Panel> */}
     ) : (
       <button onClick={() => setIsActive(true)}>Show</button>
     )}
   </section>
   ```

### Rendering It

When rendered, here's what happens:

For `<Panel title="About">...</Panel>`:

- The `title="About"` becomes `{title}` in `<h3>{title}</h3>`, so it shows:
  
  ```textile
  About
  ```

- The text inside `<Panel>` becomes `{children}`, so if you click "Show," it displays:
  
  ```textile
  With a population of about 2 million...
  ```

For `<Panel title="Etymology">...</Panel>`:

- The `title="Etymology"` becomes `{title}`, so it shows:
  
  ```textile
  Etymology
  ```

- The text inside `<Panel>` becomes `{children}`, so if you click "Show," it displays:
  
  ```textile
  The name comes from алма...
  ```

## Decoupled Accordion

This approach removes hardcoding and makes the component reusable for any set of panels.

- The `Accordion` component imports `panelsData.json`.
- It maps over the JSON data to render each `Panel` dynamically.
- The `activeIndex` state determines which panel is expanded.

```json
[
  {
    "index": 0,
    "title": "About",
    "description": "With a population of about 2 million, Almaty is Kazakhstan's largest city. From 1929 to 1997, it was its capital city."
  },
  {
    "index": 1,
    "title": "Etymology",
    "description": "The name comes from алма, the Kazakh word for 'apple' and is often translated as 'full of apples'. The region is thought to be the ancestral home of the apple."
  },
  {
    "index": 2,
    "title": "Geography",
    "description": "Almaty is located in the foothills of the Trans-Ili Alatau mountains in southern Kazakhstan."
  },
  {
    "index": 3,
    "title": "Culture",
    "description": "Almaty is known as Kazakhstan's cultural and financial hub, with numerous museums, theaters, and events."
  }
]
```

```jsx
import { useState } from 'react';
import panelsData from './panelsData.json';

export default function Accordion() {
  const [activeIndex, setActiveIndex] = useState(0);

  return (
    <>
      <h2>Almaty, Kazakhstan</h2>
      {panelsData.map((panel) => (
        <Panel
          key={panel.index}
          title={panel.title}
          isActive={activeIndex === panel.index}
          onShow={() => setActiveIndex(panel.index)}
        >
          {panel.description}
        </Panel>
      ))}
    </>
  );
}

function Panel({ title, children, isActive, onShow }) {
  return (
    <section className="panel">
      <h3>{title}</h3>
      {isActive ? (
        <p>{children}</p>
      ) : (
        <button onClick={onShow}>Show</button>
      )}
    </section>
  );
}
```

## Using Reducers for Complex State

For more complex state management, especially if you have additional operations:

```jsx
const [state, dispatch] = useReducer(reducer, initialState);

function reducer(state, action) {
  switch (action.type) {
    case 'TOGGLE_PANEL':
      return { ...state, activePanel: state.activePanel === action.index ? null : action.index };
    // Other cases...
    default:
      return state;
  }
}

// In your JSX:
{panels.map((panel, index) => (
  <Panel
    key={index}
    isActive={state.activePanel === index}
    onToggle={() => dispatch({ type: 'TOGGLE_PANEL', index })}
  />
))}
```

## Context API for Deeply Nested Components

If your accordion structure is complex and deeply nested, consider using the Context API:

```jsx
const AccordionContext = createContext();

function AccordionProvider({ children }) {
  const [activePanel, setActivePanel] = useState(null);
  return (
    <AccordionContext.Provider value={{ activePanel, setActivePanel }}>
      {children}
    </AccordionContext.Provider>
  );
}

// In your Panel component:
function Panel({ index, children }) {
  const { activePanel, setActivePanel } = useContext(AccordionContext);
  const isActive = activePanel === index;
  const togglePanel = () => setActivePanel(isActive ? null : index);

  // Render panel...
}
```

## Using Local Storage for Accordion State

This approach is particularly useful for persisting the state of the accordion across page reloads or even browser sessions. Here's how you can implement this: 

1. **Set up state with localStorage**:

```jsx
import { useState, useEffect } from 'react';

const [activePanels, setActivePanels] = useState(() => {
  // Try to get the state from localStorage
  const saved = localStorage.getItem('accordionState');
  return saved ? JSON.parse(saved) : new Set();
});
```

2. **Update localStorage when state changes**:

```jsx
useEffect(() => {
  localStorage.setItem('accordionState', JSON.stringify(Array.from(activePanels)));
}, [activePanels]);
```

3. **Toggle function for panels**:

```jsx
const togglePanel = (index) => {
  setActivePanels(prev => {
    const newSet = new Set(prev);
    if (newSet.has(index)) {
      newSet.delete(index);
    } else {
      newSet.add(index);
    }
    return newSet;
  });
};
```

4. **Render panels**:

```jsx
{panels.map((panel, index) => (
  <Panel
    key={index}
    isActive={activePanels.has(index)}
    onToggle={() => togglePanel(index)}
    {...panel}
  />
))}
```

**Complete Example**

Here's a complete example incorporating these concepts:

```jsx
import React, { useState, useEffect } from 'react';

function Accordion({ panels }) {
  const [activePanels, setActivePanels] = useState(() => {
    const saved = localStorage.getItem('accordionState');
    return saved ? new Set(JSON.parse(saved)) : new Set();
  });

  useEffect(() => {
    localStorage.setItem('accordionState', JSON.stringify(Array.from(activePanels)));
  }, [activePanels]);

  const togglePanel = (index) => {
    setActivePanels(prev => {
      const newSet = new Set(prev);
      if (newSet.has(index)) {
        newSet.delete(index);
      } else {
        newSet.add(index);
      }
      return newSet;
    });
  };

  return (
    <div>
      {panels.map((panel, index) => (
        <Panel
          key={index}
          title={panel.title}
          isActive={activePanels.has(index)}
          onToggle={() => togglePanel(index)}
        >
          {panel.content}
        </Panel>
      ))}
    </div>
  );
}

function Panel({ title, children, isActive, onToggle }) {
  return (
    <div className="panel">
      <h3 onClick={onToggle}>{title}</h3>
      {isActive && <div className="panel-content">{children}</div>}
    </div>
  );
}

export default Accordion;
```

**Benefits and Considerations**

- **Persistence**: The accordion state persists across page reloads.
- **User Experience**: Users can return to the page and find the accordion in the same state they left it.
- **Performance**: Be mindful of the size of data you're storing in localStorage. For large datasets, consider alternative storage methods.
- **Privacy Mode**: localStorage might not work in private browsing modes of some browsers.

Remember to <mark>handle potential errors when working with `localStorage`</mark>, as it might be disabled in some browser settings.
