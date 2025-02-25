# Accordion

## Introduction

Links:

- [Sharing State Between Components](https://react.dev/learn/sharing-state-between-components)
- [Controlled and uncontrolled components](https://react.dev/learn/sharing-state-between-components#controlled-and-uncontrolled-components)
- [Recap](https://react.dev/learn/sharing-state-between-components#recap "Link for Recap")
- [CodeSandbox code](https://codesandbox.io/p/sandbox/nt8zkz?file=%2Fsrc%2FFAQ.jsx)
- [CodeSandbox render deploy](https://nt8zkz.csb.app/)
  
  

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

#### What is `children`?

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

## React.dev code

```jsx
import { useState } from 'react';

export default function Accordion() {
  const [activeIndex, setActiveIndex] = useState(0);
  return (
    <>
      <h2>Almaty, Kazakhstan</h2>
      <Panel
        title="About"
        isActive={activeIndex === 0}
        onShow={() => setActiveIndex(0)}
      >
        With a population of about 2 million, Almaty is Kazakhstan's largest city. From 1929 to 1997, it was its capital city.
      </Panel>
      <Panel
        title="Etymology"
        isActive={activeIndex === 1}
        onShow={() => setActiveIndex(1)}
      >
        The name comes from <span lang="kk-KZ">алма</span>, the Kazakh word for "apple" and is often translated as "full of apples". In fact, the region surrounding Almaty is thought to be the ancestral home of the apple, and the wild <i lang="la">Malus sieversii</i> is considered a likely candidate for the ancestor of the modern domestic apple.
      </Panel>
    </>
  );
}

function Panel({
  title,
  children,
  isActive,
  onShow
}) {
  return (
    <section className="panel">
      <h3>{title}</h3>
      {isActive ? (
        <p>{children}</p>
      ) : (
        <button onClick={onShow}>
          Show
        </button>
      )}
    </section>
  );
}
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

### LocalStorage

`localStorage` is a web storage object in browsers that allows web applications to <mark>store key-value pairs locally within the user's browser</mark>. Here's a breakdown of how it works:

1. **Purpose**:
   
   - It's used to store data on the client-side (in the browser) that persists even when the browser is closed and reopened.

2. **Key-Value Storage** & **Persistence**: :
   
   - Data is stored as strings in key-value pairs.
   
   - Example: `localStorage.setItem('username', 'John')` stores 'John' under the key 'username'.
   
   - Data stored in localStorage remains available until it's cleared through JavaScript or the user clears their browser data.

3. **Domain Specific** & **Capacity**::
   
   - Each website has its own separate localStorage. Data stored by one site cannot be accessed by another site.
   
   - Typically has a limit of about 5-10 MB, depending on the browser.

4. **Usage**:
   
   - Set item: `localStorage.setItem('key', 'value')`
   
   - Get item: `localStorage.getItem('key')`
   
   - Remove item: `localStorage.removeItem('key')`
   
   - Clear all: `localStorage.clear()`

5. **String Only**:
   
   - localStorage only stores strings. To store objects or arrays, you need to convert them to strings (usually using JSON.stringify) and parse them back when retrieving.

6. **Synchronous**:
   
   - Operations on localStorage are synchronous, meaning they execute immediately and can potentially block other operations if dealing with large amounts of data.

7. **Security**:
   
   - Not suitable for sensitive data as it's stored in plain text and can be accessed by any JavaScript code on the page.

In our FAQ example, `localStorage` is used to remember which panels were open, providing a consistent user experience across page reloads or revisits to the site.

### Code

`localStorage` is used in this example <mark>to persist the state of the accordion</mark> across page reloads or browser sessions.

1. **Initial State Setup**:
   
   ```jsx
   const [activePanels, setActivePanels] = useState(() => {
     const saved = localStorage.getItem('accordionState');
     return saved ? new Set(JSON.parse(saved)) : new Set();
   });
   
   ```
   
   - When the component first mounts, it tries to retrieve the saved state from localStorage.
   
   - If there's saved data, it's parsed from JSON and converted to a Set.
   
   - If there's no saved data, an empty Set is created.

2. **Updating localStorage**:
   
   ```jsx
   useEffect(() => {
     localStorage.setItem('accordionState', JSON.stringify(Array.from(activePanels)));
   }, [activePanels]);
   
   ```
   
   - This `useEffect` hook runs whenever `activePanels` changes.
   
   - It converts the Set to an array and then to a `JSON` string.
   
   - This string is then saved in localStorage under the key 'accordionState'.

Here's how it works in practice:

1. **First Visit**:
   
   - No data in localStorage, so `activePanels` starts as an empty Set.

2. **User Interaction**:
   
   - As the user opens/closes panels, `activePanels` is updated.
   
   - Each update triggers the useEffect, saving the new state to localStorage.

3. **Page Reload or Revisit**:
   
   - The saved state is retrieved from localStorage.
   
   - `activePanels` is initialized with this saved state.
   
   - The accordion appears in the same state as when the user last interacted with it.

4. **Data Persistence**:
   
   - `localStorage` data <mark>persists</mark> even when the browser is closed.
   
   - It's specific to the origin (domain) of the web page.

5. **Limitations**:
   
   - `localStorage` has a size limit (usually about **5MB-10MB**).
   
   - It's synchronous, <mark>so large operations could potentially block the main thread</mark>.



### Toogle

> In essence, this function toggles the visibility of a FAQ item. If the item was visible (in the Set), it becomes hidden (removed from the Set), and vice versa. This approach allows multiple panels to be open simultaneously while providing individual control over each panel's visibility.

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

1. **Function Definition**:  
   `togglePanel` is a function that takes an `index` as an argument. This index represents the FAQ item being toggled.

2. **State Update**:  
   It uses `setActivePanels` to update the state. In React, when updating state based on the previous state, it's recommended to use a function inside the state setter.

3. **Previous State**:  
   `prev => { ... }` is a function that receives the previous state (`prev`) as an argument.

4. **Creating a New Set**:  
   `const newSet = new Set(prev);` creates a new Set from the previous state. This ensures we're not mutating the original state directly, which is important for React's state management.

5. **Toggling Logic**:
   
   - `if (newSet.has(index))`: Checks if the Set already contains the index.
   
   - If it does, `newSet.delete(index)` removes it (closing the panel).
   
   - If it doesn't, `newSet.add(index)` adds it (opening the panel).

6. **Returning New State**:  
   `return newSet;` returns the new Set, which becomes the new state for `activePanels`.

### Why set

> Using a `Set` makes the code more intuitive and performant for this particular scenario compared to alternatives like arrays or objects.

In this specific use case of managing which FAQ panels are open, a `Set` is particularly apt because:

- Each panel is either open or closed (binary state).
- The order of open panels doesn't matter.
- We need to frequently check if a specific panel is open.
- We want to easily toggle panels without complex state management.

Using a Set in this context serves several important purposes:

1. **Unique Values**:  
   <mark>A Set only stores unique values.</mark> This ensures that each FAQ item can only be in one state (open or closed) at a time. You can't accidentally add the same index twice.

2. **Efficient Lookups**:  
   Checking if an item exists in a Set (`has()` method)<mark> is very fast</mark>, with a time complexity of O(1). This makes the toggling operation quick, regardless of how many panels are open.

3. **Easy Addition and Removal**:  
   Adding (`add()`) and removing (`delete()`) items from a Set is straightforward and efficient. This simplifies the logic for toggling panels on and off.

4. **Iterability**:  
   Sets are iterable, which makes it easy to work with them in React components, especially when rendering multiple items based on their state.

5. **Immutability-Friendly**:  
   Creating a new `Set` from the previous one (`new Set(prev)`)<mark> is an easy way to maintain immutability</mark>, which is a key principle in React for efficient rendering and state management.

6. **Flexible Size**:  
   Unlike an array where you might need to keep track of indices, a `Set` can easily grow or shrink as needed without leaving "gaps" or requiring index management.

7. **Clear Semantics**:  
   Using a `Set` clearly communicates the intent: we're dealing with a collection of unique items (open panels) that can be added or removed individually.
