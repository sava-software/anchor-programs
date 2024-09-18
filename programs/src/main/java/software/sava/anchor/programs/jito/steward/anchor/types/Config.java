package software.sava.anchor.programs.jito.steward.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// Config is a user-provided keypair.
// This is so there can be multiple configs per stake pool, and one party can't
// squat a config address for another party's stake pool.
public record Config(PublicKey _address,
                     Discriminator discriminator,
                     // SPL Stake Pool address that this program is managing
                     PublicKey stakePool,
                     // Validator List
                     PublicKey validatorList,
                     // Admin
                     // - Update the `parameters_authority`
                     // - Update the `blacklist_authority`
                     // - Can call SPL Passthrough functions
                     // - Can pause/reset the state machine
                     PublicKey admin,
                     // Parameters Authority
                     // - Can update steward parameters
                     PublicKey parametersAuthority,
                     // Blacklist Authority
                     // - Can add to the blacklist
                     // - Can remove from the blacklist
                     PublicKey blacklistAuthority,
                     // Bitmask representing index of validators that are not allowed delegation
                     // NOTE: This is indexed off of the validator history, NOT the validator list
                     LargeBitMask validatorHistoryBlacklist,
                     // Parameters for scoring, delegation, and state machine
                     Parameters parameters,
                     // Halts any state machine progress
                     U8Bool paused,
                     // Padding for future governance parameters
                     byte[] padding) implements Borsh {

  public static final int BYTES = 4048;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(155, 12, 170, 224, 30, 250, 204, 130);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int STAKE_POOL_OFFSET = 8;
  public static final int VALIDATOR_LIST_OFFSET = 40;
  public static final int ADMIN_OFFSET = 72;
  public static final int PARAMETERS_AUTHORITY_OFFSET = 104;
  public static final int BLACKLIST_AUTHORITY_OFFSET = 136;
  public static final int VALIDATOR_HISTORY_BLACKLIST_OFFSET = 168;
  public static final int PARAMETERS_OFFSET = 2672;
  public static final int PAUSED_OFFSET = 3024;
  public static final int PADDING_OFFSET = 3025;

  public static Filter createStakePoolFilter(final PublicKey stakePool) {
    return Filter.createMemCompFilter(STAKE_POOL_OFFSET, stakePool);
  }

  public static Filter createValidatorListFilter(final PublicKey validatorList) {
    return Filter.createMemCompFilter(VALIDATOR_LIST_OFFSET, validatorList);
  }

  public static Filter createAdminFilter(final PublicKey admin) {
    return Filter.createMemCompFilter(ADMIN_OFFSET, admin);
  }

  public static Filter createParametersAuthorityFilter(final PublicKey parametersAuthority) {
    return Filter.createMemCompFilter(PARAMETERS_AUTHORITY_OFFSET, parametersAuthority);
  }

  public static Filter createBlacklistAuthorityFilter(final PublicKey blacklistAuthority) {
    return Filter.createMemCompFilter(BLACKLIST_AUTHORITY_OFFSET, blacklistAuthority);
  }

  public static Filter createPausedFilter(final U8Bool paused) {
    return Filter.createMemCompFilter(PAUSED_OFFSET, paused.write());
  }

  public static Config read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Config read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Config> FACTORY = Config::read;

  public static Config read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var stakePool = readPubKey(_data, i);
    i += 32;
    final var validatorList = readPubKey(_data, i);
    i += 32;
    final var admin = readPubKey(_data, i);
    i += 32;
    final var parametersAuthority = readPubKey(_data, i);
    i += 32;
    final var blacklistAuthority = readPubKey(_data, i);
    i += 32;
    final var validatorHistoryBlacklist = LargeBitMask.read(_data, i);
    i += Borsh.len(validatorHistoryBlacklist);
    final var parameters = Parameters.read(_data, i);
    i += Borsh.len(parameters);
    final var paused = U8Bool.read(_data, i);
    i += Borsh.len(paused);
    final var padding = new byte[1023];
    Borsh.readArray(padding, _data, i);
    return new Config(_address,
                      discriminator,
                      stakePool,
                      validatorList,
                      admin,
                      parametersAuthority,
                      blacklistAuthority,
                      validatorHistoryBlacklist,
                      parameters,
                      paused,
                      padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    stakePool.write(_data, i);
    i += 32;
    validatorList.write(_data, i);
    i += 32;
    admin.write(_data, i);
    i += 32;
    parametersAuthority.write(_data, i);
    i += 32;
    blacklistAuthority.write(_data, i);
    i += 32;
    i += Borsh.write(validatorHistoryBlacklist, _data, i);
    i += Borsh.write(parameters, _data, i);
    i += Borsh.write(paused, _data, i);
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
