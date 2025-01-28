package software.sava.anchor.programs.glam_v0.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FundManagerFieldName implements Borsh.Enum {

  PortfolioManagerForename,
  PortfolioManagerName,
  PortfolioManagerYearOfBirth,
  PortfolioManagerYearOfExperienceStart,
  PortfolioManagerBriefBiography,
  PortfolioManagerType,
  PortfolioManagerRoleStartingDate,
  PortfolioManagerRoleEndDate;

  public static FundManagerFieldName read(final byte[] _data, final int offset) {
    return Borsh.read(FundManagerFieldName.values(), _data, offset);
  }
}
