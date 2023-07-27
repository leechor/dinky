export type Parameter = {
  code: string,
  type: string | null;
  name: string;
  group: string;
  feature: {
    icon: string;
  };
  specification: string;
  ports: { inputs: any; outputs: any };
};
