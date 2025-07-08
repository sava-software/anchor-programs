package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.accounts.PublicKey;

import java.util.OptionalInt;

public final class StateModelBuilder {

  private PublicKey id;
  private AccountType accountType;
  private String name;
  private String uri;
  private Boolean enabled;
  private PublicKey[] assets;
  private MintModel[] mints;
  private CompanyModel company;
  private ManagerModel owner;
  private CreatedModel created;
  private PublicKey baseAsset;
  private OptionalInt updateTimelock = OptionalInt.empty();
  private TimeUnit timeUnit;
  private DelegateAcl[] delegateAcls;
  private Integration[] integrations;
  private PublicKey[] borrowableAssets;
  private int[] driftMarketIndexesPerp;
  private int[] driftMarketIndexesSpot;
  private int[] driftOrderTypes;
  private PublicKey[] kaminoLendingMarkets;
  private PublicKey[] meteoraDlmmPools;
  private OptionalInt maxSwapSlippageBps = OptionalInt.empty();
  private Metadata metadata;
  private FundOpenfundsModel rawOpenfunds;

  public StateModelBuilder() {
  }

  public StateModelBuilder id(final PublicKey id) {
    this.id = id;
    return this;
  }

  public StateModelBuilder accountType(final AccountType accountType) {
    this.accountType = accountType;
    return this;
  }

  public StateModelBuilder name(final String name) {
    this.name = name;
    return this;
  }

  public StateModelBuilder uri(final String uri) {
    this.uri = uri;
    return this;
  }

  public StateModelBuilder enabled(final Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  public StateModelBuilder assets(final PublicKey[] assets) {
    this.assets = assets;
    return this;
  }

  public StateModelBuilder mints(final MintModel[] mints) {
    this.mints = mints;
    return this;
  }

  public StateModelBuilder company(final CompanyModel company) {
    this.company = company;
    return this;
  }

  public StateModelBuilder owner(final ManagerModel owner) {
    this.owner = owner;
    return this;
  }

  public StateModelBuilder created(final CreatedModel created) {
    this.created = created;
    return this;
  }

  public StateModelBuilder baseAsset(final PublicKey baseAsset) {
    this.baseAsset = baseAsset;
    return this;
  }

  public StateModelBuilder updateTimelock(final OptionalInt updateTimelock) {
    this.updateTimelock = updateTimelock;
    return this;
  }

  public StateModelBuilder updateTimelock(final int updateTimelock) {
    this.updateTimelock = OptionalInt.of(updateTimelock);
    return this;
  }

  public StateModelBuilder timeUnit(final TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
    return this;
  }

  public StateModelBuilder delegateAcls(final DelegateAcl[] delegateAcls) {
    this.delegateAcls = delegateAcls;
    return this;
  }

  public StateModelBuilder integrations(final Integration[] integrations) {
    this.integrations = integrations;
    return this;
  }

  public StateModelBuilder borrowableAssets(final PublicKey[] borrowableAssets) {
    this.borrowableAssets = borrowableAssets;
    return this;
  }

  public StateModelBuilder driftMarketIndexesPerp(final int[] driftMarketIndexesPerp) {
    this.driftMarketIndexesPerp = driftMarketIndexesPerp;
    return this;
  }

  public StateModelBuilder driftMarketIndexesSpot(final int[] driftMarketIndexesSpot) {
    this.driftMarketIndexesSpot = driftMarketIndexesSpot;
    return this;
  }

  public StateModelBuilder driftOrderTypes(final int[] driftOrderTypes) {
    this.driftOrderTypes = driftOrderTypes;
    return this;
  }

  public StateModelBuilder kaminoLendingMarkets(final PublicKey[] kaminoLendingMarkets) {
    this.kaminoLendingMarkets = kaminoLendingMarkets;
    return this;
  }

  public StateModelBuilder meteoraDlmmPools(final PublicKey[] meteoraDlmmPools) {
    this.meteoraDlmmPools = meteoraDlmmPools;
    return this;
  }

  public StateModelBuilder maxSwapSlippageBps(final OptionalInt maxSwapSlippageBps) {
    this.maxSwapSlippageBps = maxSwapSlippageBps;
    return this;
  }

  public StateModelBuilder maxSwapSlippageBps(final int maxSwapSlippageBps) {
    this.maxSwapSlippageBps = OptionalInt.of(maxSwapSlippageBps);
    return this;
  }

  public StateModelBuilder metadata(final Metadata metadata) {
    this.metadata = metadata;
    return this;
  }

  public StateModelBuilder rawOpenfunds(final FundOpenfundsModel rawOpenfunds) {
    this.rawOpenfunds = rawOpenfunds;
    return this;
  }

  public StateModel build() {
    return StateModel.createRecord(
        id,
        accountType,
        name,
        uri,
        enabled,
        assets,
        mints,
        company,
        owner,
        created,
        baseAsset,
        updateTimelock,
        timeUnit,
        delegateAcls,
        integrations,
        borrowableAssets,
        driftMarketIndexesPerp,
        driftMarketIndexesSpot,
        driftOrderTypes,
        kaminoLendingMarkets,
        meteoraDlmmPools,
        maxSwapSlippageBps,
        metadata,
        rawOpenfunds
    );
  }
}
