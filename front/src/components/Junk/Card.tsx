import React, { FunctionComponent } from 'react' // we need this to make JSX compile

type CardProps = {
  title: string
  paragraph?: string
}

export const Card: FunctionComponent<CardProps> = ({ title, paragraph, children }) => (
  <aside>
    <h2>{title}</h2>
    <p>{paragraph}</p>
    {children}
  </aside>
)
