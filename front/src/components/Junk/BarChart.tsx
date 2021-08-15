import React, { useRef, FunctionComponent, useEffect } from 'react'
import * as d3 from 'd3'
import { select, selectAll } from 'd3'

type BarChartState = {
  data: string
}

export const BarChart: FunctionComponent<BarChartState> = ({ data }) => {
  const svgRef = useRef<SVGSVGElement | null>(null)
  useEffect(() => {
    console.log(select(svgRef.current))
    // select(svgRef.current)
    // .append('rect')
    // .attr('width', 100)
    // .attr('height', 100)
    // .attr('fill', 'blue')

    selectAll('rect')
      .attr('width', 100)
      .attr('height', 100)
      .attr('fill', 'blue')
      .attr('x', (_, i) => i * 101)
  })
  return (
    <div>
      <svg ref={svgRef}>
        <rect />
      </svg>
    </div>
  )
}
